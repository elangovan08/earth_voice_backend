# EcoBlog Enterprise Upgrade Plan

This document tracks the production-grade upgrade path for EcoBlog.

## Completed Foundation Slice

### Frontend

- Added application-level error boundary.
- Added theme provider with persisted light/dark mode.
- Added notification provider for global toast-style feedback.
- Added lazy route loading and route-level code splitting.
- Added Framer Motion page transitions.
- Added reading progress indicator in the app layout.
- Added reusable UI primitives:
  - `Button`
  - `Card`
  - `Skeleton`
- Added reusable hooks:
  - `useDebounce`
  - `useReadingProgress`
  - `useRecentlyViewedPosts`
- Reworked navbar with glassmorphism, dark mode toggle, sticky animation, and responsive mobile menu.
- Reworked post cards with premium hover states, lazy images, metadata, and memoization.
- Reworked posts page with:
  - debounced search
  - search suggestions
  - sorting
  - category filter chips
  - skeleton loaders
  - trending panel
  - recently viewed panel
  - incremental load-more pagination
- Reworked post detail page with:
  - premium article layout
  - share action
  - recently viewed tracking

### Backend

- Added API constants package.
- Added generic `ApiResponseDTO<T>`.
- Added `PageResponseDTO<T>`.
- Added `PostMapper` layer.
- Added `/api/v1/posts` versioned controller.
- Added paginated/searchable post endpoint:
  - `GET /api/v1/posts?q=&page=&size=&sortBy=&direction=`
- Kept existing `/api/posts` endpoints compatible for the current frontend.
- Added profile configuration files:
  - `application-dev.properties`
  - `application-prod.properties`
  - `application-test.properties`
- Added caching and JPA auditing enablement.

## Completed Phase 2 Security Slice

- Added signed JWT access tokens to login/signup responses.
- Added stateless Spring Security JWT request authentication.
- Added database-backed `UserDetailsService`.
- Added role-based admin authorization for `/api/admin/**`.
- Protected post/comment write endpoints.
- Added self-or-admin checks for user-bound actions.
- Added owner-or-admin checks for post and comment deletion/editing.
- Added frontend bearer token persistence and request injection.
- Added focused JWT signing/tamper unit tests.

## Recommended Next Phases

### Phase 2: Authentication and Security

- Add refresh token entity, repository, and service.
- Add refresh-token rotation.
- Add logout invalidation.
- Add secure headers.
- Add auth rate limiting.

### Phase 3: User Platform Features

- Add user profile entity fields:
  - profile image
  - bio
  - social links
  - activity stats
- Add follow/unfollow users.
- Add notification entity and APIs.
- Add bookmark collections.

### Phase 4: Editorial System

- Add React Quill or EditorJS.
- Add sanitized rich-text rendering.
- Add markdown/rich text previews.
- Add post categories and tags.
- Add recommended posts API.

### Phase 5: Media and Analytics

- Add Cloudinary upload service.
- Add image upload endpoint.
- Add analytics aggregation service.
- Add dashboard charts for:
  - user growth
  - most liked posts
  - most active users
  - monthly activity
  - category analytics

### Phase 6: AI Features

- Add backend-only AI integration.
- Add APIs for:
  - title suggestions
  - content summary
  - grammar suggestions
  - toxic comment detection

### Phase 7: SEO, PWA, Deployment

- Add dynamic document titles and OpenGraph metadata.
- Add sitemap and robots.txt.
- Add PWA service worker and offline fallback.
- Add Vercel/Netlify frontend deployment config.
- Add Render/Railway backend production config.
- Add CI workflows for build and test.
