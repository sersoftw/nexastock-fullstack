export type Role = 'ADMIN' | 'MANAGER';

export interface AuthResponse {
  token: string;
  email: string;
  fullName: string;
  role: Role;
}

export interface Product {
  id: number;
  sku: string;
  name: string;
  category: string;
  price: number;
  stock: number;
  minStock: number;
  lowStock: boolean;
  active: boolean;
}

export interface Customer {
  id: number;
  name: string;
  email: string;
  phone?: string;
  company?: string;
  active: boolean;
}

export interface OrderItem {
  productId: number;
  productName: string;
  quantity: number;
  unitPrice: number;
  lineTotal: number;
}

export interface Order {
  id: number;
  orderNumber: string;
  customerId: number;
  customerName: string;
  status: 'DRAFT' | 'CONFIRMED' | 'SHIPPED' | 'CANCELLED';
  totalAmount: number;
  createdAt: string;
  items: OrderItem[];
}

export interface Dashboard {
  activeProducts: number;
  activeCustomers: number;
  pendingOrders: number;
  totalRevenue: number;
  lowStockProducts: Product[];
  recentOrders: Order[];
}

export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}
