import { FormEvent, useEffect, useState } from 'react';
import api from '../services/api';
import { Customer, Page, Product } from '../types';

interface DraftLine {
  productId: number;
  quantity: number;
}

export default function NewOrderPage() {
  const [customers, setCustomers] = useState<Customer[]>([]);
  const [products, setProducts] = useState<Product[]>([]);
  const [customerId, setCustomerId] = useState<number | ''>('');
  const [lines, setLines] = useState<DraftLine[]>([{ productId: 0, quantity: 1 }]);
  const [message, setMessage] = useState('');

  useEffect(() => {
    api.get<Page<Customer>>('/customers').then((response) => setCustomers(response.data.content));
    api.get<Page<Product>>('/products').then((response) => setProducts(response.data.content));
  }, []);

  function updateLine(index: number, value: Partial<DraftLine>) {
    setLines((current) => current.map((line, i) => i === index ? { ...line, ...value } : line));
  }

  function addLine() {
    setLines((current) => [...current, { productId: 0, quantity: 1 }]);
  }

  async function handleSubmit(event: FormEvent) {
    event.preventDefault();
    setMessage('');
    try {
      await api.post('/orders', {
        customerId,
        items: lines.filter((line) => line.productId > 0)
      });
      setCustomerId('');
      setLines([{ productId: 0, quantity: 1 }]);
      setMessage('Pedido creado correctamente y stock actualizado.');
    } catch {
      setMessage('No se ha podido crear el pedido. Comprueba cliente, productos y stock.');
    }
  }

  return (
    <section>
      <div className="page-heading">
        <div>
          <p className="eyebrow">Ventas</p>
          <h2>Nuevo pedido</h2>
        </div>
      </div>

      <article className="panel narrow-panel">
        <form className="stack-form" onSubmit={handleSubmit}>
          <label>
            Cliente
            <select value={customerId} onChange={(e) => setCustomerId(Number(e.target.value))}>
              <option value="">Selecciona un cliente</option>
              {customers.map((customer) => (
                <option key={customer.id} value={customer.id}>{customer.name}</option>
              ))}
            </select>
          </label>

          <h3>Líneas</h3>
          {lines.map((line, index) => (
            <div className="line-row" key={index}>
              <select value={line.productId} onChange={(e) => updateLine(index, { productId: Number(e.target.value) })}>
                <option value={0}>Producto</option>
                {products.map((product) => (
                  <option key={product.id} value={product.id}>
                    {product.name} · stock {product.stock}
                  </option>
                ))}
              </select>
              <input type="number" min="1" value={line.quantity} onChange={(e) => updateLine(index, { quantity: Number(e.target.value) })} />
            </div>
          ))}

          <button type="button" className="secondary-button" onClick={addLine}>Añadir línea</button>
          <button>Crear pedido</button>
          {message && <p className="form-message">{message}</p>}
        </form>
      </article>
    </section>
  );
}
