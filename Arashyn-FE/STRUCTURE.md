# `src` Directory Structure

## 1. Overview

```
src/
├── app/
│   └── router/
├── components/
│   ├── background/
│   ├── item/
│   └── ui/
│       └── variants/
├── entities/
│   └── <entity_name>/ (ví dụ: auth, grammar...)
│       ├── <entity>_service.ts
│       ├── <entity>_transform.ts
│       ├── <entity>_hooks.ts
│       └── <entity>_types.ts
├── features/
│   └── <feature_name>/ (ví dụ: login, detail, home...)
│       ├── components/
│       ├── constants/
│       ├── hooks/
│       ├── parts/
│       └── types/
├── layout/
│   └── <layout_name>/ (ví dụ: sidebar, topbar...)
│       ├── components/
│       ├── constants/
│       ├── contexts/
│       └── types/
├── lib/
│   ├── api/
│   │   ├── http.ts
│   │   ├── request.ts
│   │   └── types.ts
│   └── query/
├── mocks/
│   ├── constants/
│   ├── handlers/
│   └── types/
│       ├── request/
│       └── response/
├── page/
├── shared/
│   ├── constants/
│   ├── hooks/
│   ├── store/
│   └── validation/
└── styles/
```

---

## 2. Top-Level Packages

| Package | Purpose |
|---|---|
| `app/` | Application bootstrap: routing setup, global providers, entrypoint. |
| `components/` | Pure, domain-agnostic UI components reusable anywhere in the app. |
| `entities/` | Business domain entities (e.g. `grammar`, `auth`). Each entity owns its own data fetching, transformation, and typing. |
| `features/` | Product-specific functional areas (e.g. `login`, `detail`, `popular`). Orchestrates entities to build a specific user-facing capability. |
| `layout/` | Reusable page scaffolding shared across multiple pages (sidebar, topbar, home layout). |
| `lib/` | Technical infrastructure with no business logic: HTTP client, query client configuration. |
| `mocks/` | MSW-based mock server, intercepting requests at the network layer. |
| `page/` | Route-level components that compose `layout` and `features` into complete pages. |
| `shared/` | Cross-cutting utilities and state used by multiple entities/features, with no UI and no domain-specific logic. |
| `styles/` | Global styles, theme tokens, and CSS/Tailwind configuration. |

---

## 3. `entities/`

Each entity is a flat directory (no sub-packages — one file per responsibility):

| File | Purpose |
|---|---|
| `<entity>_service.ts` | Raw API calls for this domain. The only place allowed to call `lib/api`. |
| `<entity>_transform.ts` | Pure functions transforming raw API responses into view models. Framework-agnostic. |
| `<entity>_hooks.ts` | Derive hooks (`useMemo` wrapping `transform`) for use in components. Not data-fetching hooks. |
| `<entity>_types.ts` | Types for both the raw API response and the transformed view model. |

---

## 4. `features/`

Each feature contains only the sub-packages it actually needs:

| Sub-package | Purpose |
|---|---|
| `parts/` | Large page sections composed from multiple `components`. A page/feature root only arranges `parts`; it doesn't render fine-grained UI itself. |
| `components/` | Smaller UI pieces reused across multiple `parts` within the feature, or a sizable block (e.g. a list-item card) extracted out of a `part` to keep it readable. |
| `hooks/` | Feature-specific hooks: data-fetching hooks that route to the correct entity service, plus feature-local state/UI logic. |
| `constants/` | Enums/constants meaningful only within this feature. |
| `types/` | Local prop/state/param types for the feature — not API response types (those live in `entities/`). |

---

## 5. `layout/`

Each layout area (`home`, `sidebar`, `topbar`) follows the same sub-package convention as a feature (`components`, `constants`, `types`). `topbar` additionally has:

| Sub-package | Purpose |
|---|---|
| `contexts/` | React Context local to the topbar (e.g. search/dropdown open state). |
| `hooks/` | Hooks handling topbar-specific logic and state. |

---

## 6. `lib/`

| Sub-package | Purpose |
|---|---|
| `api/` | HTTP client setup: axios instance, request/response interceptors (auth headers, token refresh), and shared API-layer types. |
| `query/` | React Query client instance and default configuration. |

---

## 7. `shared/`

| Sub-package | Purpose |
|---|---|
| `constants/` | App-wide constants not tied to any specific entity or feature. |
| `hooks/` | Generic utility hooks with no domain logic (`useDebounce`, `useMediaQuery`, etc.). |
| `store/` | App-wide state management (e.g. theme, global UI state). |
| `validation/` | Shared validation schemas (Zod/Yup) for forms and inputs. |

---

## 8. `mocks/`

| Sub-package | Purpose |
|---|---|
| `constants/` | Sample data used for mocking (fake grammar lists, fake users, etc.). |
| `handlers/` | MSW request handlers per entity/domain, matching the URLs called by `entities/*_service.ts`. |
| `types/request/` | Types for mock request parameters. |
| `types/response/` | Types for mock response payloads, when they diverge from `entities/*_types.ts`. |

---

## 9. Naming Conventions

- Sub-package folders always use the **plural** form: `hooks/`, `constants/`, `types/`, `components/`, `parts/`, `handlers/`.
- Entity files follow the pattern `<entity>_<role>.ts` (`service`, `transform`, `hooks`, `types`) as flat files directly under `entities/<entity>/`, with no further sub-folder nesting.