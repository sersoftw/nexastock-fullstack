import { NavLink, Outlet } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Layout() {
  const { user, logout } = useAuth();

  return (
    <div className="app-shell">
      <aside className="sidebar">
        <div>
          <p className="eyebrow">Portfolio Project</p>
          <h1>NexaStock</h1>
          <p className="sidebar-subtitle">Inventory & Orders SaaS</p>
        </div>

        <nav className="nav-list">
          <NavLink to="/" end>Dashboard</NavLink>
          <NavLink to="/products">Productos</NavLink>
          <NavLink to="/customers">Clientes</NavLink>
          <NavLink to="/orders">Pedidos</NavLink>
          <NavLink to="/orders/new">Nuevo pedido</NavLink>
        </nav>

        <div className="user-card">
          <strong>{user?.fullName}</strong>
          <span>{user?.email}</span>
          <span className="pill">{user?.role}</span>
          <button onClick={logout}>Cerrar sesión</button>
        </div>
      </aside>

      <main className="main-content">
        <Outlet />
      </main>
    </div>
  );
}
