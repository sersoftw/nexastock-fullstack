import { FormEvent, useEffect, useState } from 'react';
import api from '../services/api';
import { Page, Product } from '../types';

const emptyForm = {
  sku: '',
  name: '',
  category: '',
  price: 0,
  stock: 0,
  minStock: 0
};

export default function ProductsPage() {
  const [products, setProducts] = useState<Product[]>([]);
  const [term, setTerm] = useState('');
  const [form, setForm] = useState(emptyForm);
  const [message, setMessage] = useState('');

  function loadProducts() {
    api.get<Page<Product>>('/products', { params: { term } }).then((response) => {
      setProducts(response.data.content);
    });
  }

  useEffect(() => {
    loadProducts();
  }, []);

  async function handleSubmit(event: FormEvent) {
    event.preventDefault();
    setMessage('');
    try {
      await api.post('/products', form);
      setForm(emptyForm);
      setMessage('Producto creado correctamente.');
      loadProducts();
    } catch {
      setMessage('No se ha podido crear el producto. Revisa los datos o el rol del usuario.');
    }
  }

  return (
    <section>
      <div className="page-heading">
        <div>
          <p className="eyebrow">Gestión comercial</p>
          <h2>Productos</h2>
        </div>
        <div className="search-box">
          <input placeholder="Buscar producto" value={term} onChange={(e) => setTerm(e.target.value)} />
          <button onClick={loadProducts}>Buscar</button>
        </div>
      </div>

      <div className="content-grid">
        <article className="panel">
          <h3>Catálogo</h3>
          <table>
            <thead>
              <tr>
                <th>SKU</th>
                <th>Nombre</th>
                <th>Categoría</th>
                <th>Precio</th>
                <th>Stock</th>
              </tr>
            </thead>
            <tbody>
              {products.map((product) => (
                <tr key={product.id}>
                  <td>{product.sku}</td>
                  <td>{product.name}</td>
                  <td>{product.category}</td>
                  <td>{product.price.toFixed(2)} €</td>
                  <td>{product.lowStock ? <span className="danger-pill">{product.stock}</span> : product.stock}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </article>

        <article className="panel">
          <h3>Nuevo producto</h3>
          <form className="stack-form" onSubmit={handleSubmit}>
            <input placeholder="SKU" value={form.sku} onChange={(e) => setForm({ ...form, sku: e.target.value })} />
            <input placeholder="Nombre" value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} />
            <input placeholder="Categoría" value={form.category} onChange={(e) => setForm({ ...form, category: e.target.value })} />
            <input type="number" step="0.01" placeholder="Precio" value={form.price} onChange={(e) => setForm({ ...form, price: Number(e.target.value) })} />
            <input type="number" placeholder="Stock" value={form.stock} onChange={(e) => setForm({ ...form, stock: Number(e.target.value) })} />
            <input type="number" placeholder="Stock mínimo" value={form.minStock} onChange={(e) => setForm({ ...form, minStock: Number(e.target.value) })} />
            <button>Crear producto</button>
            {message && <p className="form-message">{message}</p>}
          </form>
        </article>
      </div>
    </section>
  );
}
