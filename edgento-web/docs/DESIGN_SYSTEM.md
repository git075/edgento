# Edgento Web — Design System & Frontend Architecture

---

## Brand Identity

| Property | Value |
|---|---|
| **Name** | Edgento |
| **Tagline** | "Technology with an edge" |
| **Personality** | Established, sophisticated, deeply trustworthy, and highly accessible. |
| **Target Audience** | Indian SMBs, agency owners, freelancers looking for reliable, high-end solutions. |
| **Design Direction** | Modern Heritage / Premium Editorial. A deliberate departure from the "dark mode SaaS" trend to convey human craftsmanship, trust, and longevity. |

---

## Design Philosophy

> **Quiet confidence.** We don't need neon lights to prove we are advanced.

To avoid the "AI-generated SaaS" look, we are moving away from dark themes, glowing borders, and neon gradients. Instead, we use a classic, highly readable, light-themed editorial approach. This signals a premium, established firm (like a top-tier consultancy or architectural studio) rather than a fleeting tech startup.

### What We AVOID (The "AI-Generated" Tropes)
- ❌ Dark mode with purple/cyan gradients.
- ❌ Glowing borders, glassmorphism, or floating blobs.
- ❌ Complex mesh or particle backgrounds.
- ❌ Overly technical, cold, or chaotic layouts.

### What We DO (Premium & Trustworthy)
- ✅ **Warm Light Backgrounds**: Highly SEO-friendly, excellent for accessibility and prolonged reading.
- ✅ **High Contrast Typography**: Deep charcoal text on warm white. 
- ✅ **Classic Accents**: Deep Midnight Blue or Heritage Green instead of neon electric blue.
- ✅ **Editorial Layouts**: Generous whitespace, stark lines, and asymmetrical balance.
- ✅ **Flat & Crisp**: Minimal shadows. Reliance on strong borders and solid colors for depth.

---

## Color Palette

### Core Palette (Warm & Light)
| CSS Token | Value | Usage |
|---|---|---|
| `--color-bg` | `hsl(40, 20%, 98%)` | Base background — a very subtle, warm off-white (alabaster). |
| `--color-bg-subtle` | `hsl(40, 15%, 94%)` | Slightly darker surface for alternating sections. |
| `--color-bg-card` | `hsl(0, 0%, 100%)` | Pure white for cards to pop against the alabaster background. |
| `--color-bg-elevated` | `hsl(0, 0%, 100%)` | Navbar, modals, dropdowns (with a stark border). |
| `--color-border` | `hsl(40, 10%, 85%)` | Subtle borders. |
| `--color-border-strong` | `hsl(40, 10%, 10%)` | Hard, architectural borders and focused inputs. |

### Text (High Contrast)
| CSS Token | Value | Usage |
|---|---|---|
| `--color-text-primary` | `hsl(220, 15%, 10%)` | Main text — deep, rich charcoal (almost black). |
| `--color-text-secondary` | `hsl(220, 10%, 40%)` | Supporting text, captions. |
| `--color-text-muted` | `hsl(220, 10%, 60%)` | Placeholders, disabled states. |

### Accent (Sophisticated Trust)
| CSS Token | Value | Usage |
|---|---|---|
| `--color-accent` | `hsl(220, 60%, 20%)` | Midnight Blue — deeply trustworthy, classic, authoritative. |
| `--color-accent-hover` | `hsl(220, 60%, 30%)` | Hover state for accent. |
| `--color-accent-subtle` | `hsl(220, 30%, 92%)` | Tinted backgrounds (badge backgrounds). |

### Status Colors
| CSS Token | Value | Usage |
|---|---|---|
| `--color-success` | `hsl(150, 60%, 30%)` | "Live" badge (Deep Emerald) |
| `--color-warning` | `hsl(30, 80%, 40%)` | "Beta" badge (Burnt Orange) |
| `--color-info` | `hsl(220, 60%, 20%)` | "Coming Soon" badge (Midnight Blue) |

---

## Typography

### Font Families
| CSS Token | Font | Usage | Why |
|---|---|---|---|
| `--font-display` | `'Instrument Serif', serif` | Hero headlines, large numbers | Elegant, editorial, establishes a human, premium tone. |
| `--font-sans` | `'Inter', sans-serif` | Body text, UI elements, small headers | Clean, highly legible, excellent for SEO and accessibility. |
| `--font-mono` | `'Geist Mono', monospace` | Code blocks, technical labels | Consistent, technical contrast. |

*(Spacing and sizing remains exactly the same as before, only colors have changed).*
