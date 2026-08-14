# Edgento Web — Coding Rules for AI Assistants

## Project Context
edgento-web is the React/Vite marketing website and product showcase for Edgento.
It includes:
1. A 7-page marketing site (Home, Services, Products, Work, About, Contact, Blog)
2. An AI diagnostic chat widget (connects to edgento-api via SSE)
3. The in-house product showcase (ClientOS, ClassKhata, TaxReconcile)

API Base URL: https://api.edgento.com/api/v1 (dev: http://localhost:8080/api/v1)
Routing: react-router-dom v6
Styling: Vanilla CSS with CSS custom properties (no Tailwind, no CSS-in-JS)

## Architecture

```
src/
├── pages/        ← Route-level components (one per URL)
├── components/   ← Reusable UI pieces
│   ├── layout/   ← Navbar, Footer, Layout wrapper
│   ├── ui/       ← Generic primitives (Button, Card, Input, Badge)
│   ├── sections/ ← Page-specific sections (Hero, CTASection)
│   ├── products/ ← Product showcase components
│   └── agent/    ← AI chat widget components
├── hooks/        ← Custom React hooks (useSSE, useAgentChat)
├── services/     ← API call functions (no fetch in components)
├── data/         ← Static data files (products.js)
├── styles/       ← Global CSS (index.css, animations.css)
└── utils/        ← Pure utility functions
```

## Code Rules

1. **File-level comment**: Every file starts with a comment: WHAT it is, WHY it exists, HOW it fits.
2. **No fetch in components**: All API calls go through `src/services/`. Components call service functions.
3. **No inline styles**: All styles use CSS classes from index.css or component-scoped CSS.
4. **Custom hooks for side effects**: SSE connections, API polling, and complex state go in `src/hooks/`.
5. **Props documentation**: Every component has a JSDoc comment describing its props.
6. **Named exports**: Use named exports for components (`export function Button()`), not default exports, EXCEPT for page components which use default exports.

## CSS Conventions
- Use CSS custom properties (variables) defined in index.css: --color-primary, --color-bg, etc.
- BEM-like class naming: `.product-card`, `.product-card__title`, `.product-card--featured`
- Mobile-first: base styles are mobile, use `@media (min-width: 768px)` for desktop.

## Component Rules
- Functional components only (no class components)
- useState and useEffect are fine in components; complex logic goes in custom hooks
- Prop types are documented via JSDoc (we add TypeScript in a later phase)

## When Adding a New Page
1. Create `src/pages/NewPage.jsx`
2. Add route in `src/App.jsx`
3. Add nav link in `src/components/layout/Navbar.jsx`
4. Update `src/components/layout/Footer.jsx` if needed

## When Adding a New API Endpoint
1. Add the function to the appropriate service (`src/services/leadService.js`, etc.)
2. Call the service function from a component or custom hook
3. NEVER call fetch/axios directly in a component

## Product Data
- Product data lives in `src/data/products.js`
- To add a new product, add an entry to the `products` array
- Status values: 'live' | 'beta' | 'coming-soon'
