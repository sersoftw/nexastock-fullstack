import { useEffect, useState } from 'react';
import api from '../services/api';
import { Dashboard } from '../types';

export default function DashboardPage() {
  const [dashboard, setDashboard] = useState<Dashboard | null>(null);

  useEffect(() => {
    api.get<Dashboard>('/dashboard').then((response) => setDashboard(response.data));
  }, []);

  if (!dashboard) {
    return <p>Cargando dashboard...</p>;
  }

  return (
    <section>
      <div className="page-heading">
        <div>
          <p className="eyebrow">Panel ejecutivo</p>
          <h2>Dashboard</h2>
        </div>
      </div>

      <div className="kpi-grid">
        <article className="kpi-card">
          <span>Productos activos</span>
          <strong>{dashboard.activeProducts}</strong>
        </article>
        <article className="kpi-card">
          <span>Clientes activos</span>
          <strong>{dashboard.activeCustomers}</strong>
        </article>
        <article className="kpi-card">
          <span>Pedidos pendientes</span>
          <strong>{dashboard.pendingOrders}</strong>
        </article>
        <article className="kpi-card">
          <span>Facturación</span>
          <strong>{dashboard.totalRevenue.toFixed(2)} €</strong>
        </article>
      </div>

      <div className="content-grid">
        <article className="panel">
          <h3>Stock bajo</h3>
          {dashboard.lowStockProducts.length === 0 ? (
            <p>No hay productos con stock bajo.</p>
          ) : (
            <table>
              <thead>
                <tr>
                  <th>SKU</th>
                  <th>Producto</th>
                  <th>Stock</th>
                </tr>
              </thead>
              <tbody>
                {dashboard.lowStockProducts.map((product) => (
                  <tr key={product.id}>
                    <td>{product.sku}</td>
                    <td>{product.name}</td>
                    <td><span className="danger-pill">{product.stock}</span></td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </article>

        <article className="panel">
          <h3>Últimos pedidos</h3>
          {dashboard.recentOrders.length === 0 ? (
            <p>Aún no hay pedidos.</p>
          ) : (
            <table>
              <thead>
                <tr>
                  <th>Número</th>
                  <th>Cliente</th>
                  <th>Total</th>
                </tr>
              </thead>
              <tbody>
                {dashboard.recentOrders.map((order) => (
                  <tr key={order.id}>
                    <td>{order.orderNumber}</td>
                    <td>{order.customerName}</td>
                    <td>{order.totalAmount.toFixed(2)} €</td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </article>
      </div>
    </section>
  );
}
