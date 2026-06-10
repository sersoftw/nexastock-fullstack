import { FormEvent, useEffect, useState } from 'react';
import api from '../services/api';
import { Customer, Page } from '../types';

const emptyForm = {
  name: '',
  email: '',
  phone: '',
  company: ''
};

export default function CustomersPage() {
  const [customers, setCustomers] = useState<Customer[]>([]);
  const [term, setTerm] = useState('');
  const [form, setForm] = useState(emptyForm);
  const [message, setMessage] = useState('');

  function loadCustomers() {
    api.get<Page<Customer>>('/customers', { params: { term } }).then((response) => {
      setCustomers(response.data.content);
    });
  }

  useEffect(() => {
    loadCustomers();
  }, []);

  async function handleSubmit(event: FormEvent) {
    event.preventDefault();
    setMessage('');
    try {
      await api.post('/customers', form);
      setForm(emptyForm);
      setMessage('Cliente creado correctamente.');
      loadCustomers();
    } catch {
      setMessage('No se ha podido crear el cliente. Revisa los datos.');
    }
  }

  return (
    <section>
      <div className="page-heading">
        <div>
          <p className="eyebrow">CRM básico</p>
          <h2>Clientes</h2>
        </div>
        <div className="search-box">
          <input placeholder="Buscar cliente" value={term} onChange={(e) => setTerm(e.target.value)} />
          <button onClick={loadCustomers}>Buscar</button>
        </div>
      </div>

      <div className="content-grid">
        <article className="panel">
          <h3>Clientes activos</h3>
          <table>
            <thead>
              <tr>
                <th>Nombre</th>
                <th>Email</th>
                <th>Empresa</th>
                <th>Teléfono</th>
              </tr>
            </thead>
            <tbody>
              {customers.map((customer) => (
                <tr key={customer.id}>
                  <td>{customer.name}</td>
                  <td>{customer.email}</td>
                  <td>{customer.company}</td>
                  <td>{customer.phone}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </article>

        <article className="panel">
          <h3>Nuevo cliente</h3>
          <form className="stack-form" onSubmit={handleSubmit}>
            <input placeholder="Nombre" value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} />
            <input placeholder="Email" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} />
            <input placeholder="Empresa" value={form.company} onChange={(e) => setForm({ ...form, company: e.target.value })} />
            <input placeholder="Teléfono" value={form.phone} onChange={(e) => setForm({ ...form, phone: e.target.value })} />
            <button>Crear cliente</button>
            {message && <p className="form-message">{message}</p>}
          </form>
        </article>
      </div>
    </section>
  );
}
