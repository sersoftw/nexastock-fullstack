.PHONY: up down backend frontend test

up:
	docker compose up --build

down:
	docker compose down

backend:
	cd backend && mvn spring-boot:run

frontend:
	cd frontend && npm run dev

test:
	cd backend && mvn test
