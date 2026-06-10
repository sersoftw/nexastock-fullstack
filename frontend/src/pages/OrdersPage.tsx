import { useEffect, useState } from 'react';
import api from '../services/api';
import { Order, Page } from '../types';

export default function OrdersPage() {
  const [orders, setOrders] = useState<Order[]>([]);

  useEffect(() => {
    api.get<Page<Order>>('/orders').then((response) => setOrders(response.data.content));
  }, []);

  return (
    <section>
      <div className="page-heading">
        <div>
          <p className="eyebrow">Operaciones</p>
          <h2>Pedidos</h2>
        </div>
      </div>

      <article className="panel">
        <table>
          <thead>
            <tr>
              <th>Número</th>
              <th>Cliente</th>
              <th>Estado</th>
              <th>Fecha</th>
              <th>Total</th>
            </tr>
          </thead>
          <tbody>
            {orders.map((order) => (
              <tr key={order.id}>
                <td>{order.orderNumber}</td>
                <td>{order.customerName}</td>
                <td><span className="pill">{order.status}</span></td>
                <td>{new Date(order.createdAt).toLocaleDateString()}</td>
                <td>{order.totalAmount.toFixed(2)} €</td>
              </tr>
            ))}
          </tbody>
        </table>
      </article>
    </section>
  );
}
