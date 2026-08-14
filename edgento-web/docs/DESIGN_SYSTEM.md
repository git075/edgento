# Edgento Web — Design System

## Brand Identity
- **Name**: Edgento
- **Tagline**: "Technology with an edge"
- **Personality**: Professional, modern, trustworthy, forward-thinking
- **Target**: Indian SMBs and freelancers looking for AI-powered tools

## Color Palette

| Token | Value | Usage |
|---|---|---|
| `--color-primary` | `#6366f1` | Indigo — CTAs, links, highlights |
| `--color-primary-dark` | `#4f46e5` | Hover states for primary |
| `--color-secondary` | `#06b6d4` | Cyan — accents, badges |
| `--color-bg` | `#0f0f1a` | Deep navy-black background |
| `--color-bg-card` | `#1a1a2e` | Card/surface background |
| `--color-bg-elevated` | `#252540` | Elevated surface (navbar, modals) |
| `--color-text-primary` | `#f1f5f9` | Main text (near white) |
| `--color-text-secondary` | `#94a3b8` | Secondary/muted text |
| `--color-text-muted` | `#475569` | Disabled, placeholder text |
| `--color-border` | `#2d2d4e` | Subtle borders |
| `--color-success` | `#10b981` | Live/success badge |
| `--color-warning` | `#f59e0b` | Beta badge |
| `--color-coming-soon` | `#6366f1` | Coming soon badge |

## Typography

| Token | Value | Usage |
|---|---|---|
| `--font-display` | `'Plus Jakarta Sans', sans-serif` | Headings |
| `--font-body` | `'Inter', sans-serif` | Body text |
| `--font-mono` | `'JetBrains Mono', monospace` | Code, technical labels |

### Type Scale
| Token | Size | Usage |
|---|---|---|
| `--text-xs` | 0.75rem | Labels, badges |
| `--text-sm` | 0.875rem | Secondary text |
| `--text-base` | 1rem | Body text |
| `--text-lg` | 1.125rem | Large body |
| `--text-xl` | 1.25rem | Small headings |
| `--text-2xl` | 1.5rem | Section subheadings |
| `--text-3xl` | 1.875rem | Section headings |
| `--text-4xl` | 2.25rem | Page headings |
| `--text-5xl` | 3rem | Hero headline |
| `--text-6xl` | 3.75rem | Hero super-headline |

## Spacing
Use multiples of 4px: 4, 8, 12, 16, 24, 32, 48, 64, 96, 128px

## Border Radius
| Token | Value | Usage |
|---|---|---|
| `--radius-sm` | 4px | Small elements |
| `--radius-md` | 8px | Cards, inputs |
| `--radius-lg` | 16px | Large cards |
| `--radius-xl` | 24px | Modals, hero elements |
| `--radius-full` | 9999px | Pills, badges |

## Components

### Button
- `btn btn--primary`: Indigo filled button
- `btn btn--secondary`: Outlined button  
- `btn btn--ghost`: Text-only button
- Sizes: `btn--sm`, `btn--md` (default), `btn--lg`

### Card
- `.card`: Base card with bg-card, border, border-radius
- `.card--elevated`: Slightly lighter background
- `.card--glass`: Glassmorphism effect

### Badge / Status
- `.badge badge--live`: Green — product is live
- `.badge badge--beta`: Yellow — product is in beta
- `.badge badge--coming-soon`: Indigo — coming soon
