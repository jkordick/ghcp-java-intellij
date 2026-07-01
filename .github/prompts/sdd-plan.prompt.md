---
description: Turn an approved spec into a technical plan.
---
Goal: produce `specs/${input:storyId}/plan.md` from `specs/${input:storyId}/spec.md`.

Rules:
- Read the spec first. If it has unresolved "Open questions", stop and ask.
- Do not write code. Do not modify the spec.
- The plan covers: data model (Java records or JPA entities), package/class layout,
  REST controller interfaces, service layer, repository layer,
  external dependencies (Maven coordinates), testing strategy
  (unit with Mockito, slice tests with MockMvc), risks.
- Stop after writing the plan and wait for approval.