---
name: build-and-compile
description: Use when the user asks to compile the project, check for build errors, verify the code compiles cleanly, or ensure the project is buildable. Also use after making structural changes to catch compilation errors early.
---

1. Compile the project:

   ```bash
   mvn compile
   ```

2. If there are compilation errors:
   - Fix them before moving on
   - Run `mvn compile` again to confirm they are resolved

3. Run the full build (without running tests) to ensure packaging works:

   ```bash
   mvn package -DskipTests
   ```

4. If Checkstyle is configured in the project, run it:

   ```bash
   mvn checkstyle:check
   ```

5. If there are Checkstyle violations:
   - Fix them manually — do not use suppression annotations
   - Distinguish between errors and warnings
   - Errors must be resolved
   - Never add `@SuppressWarnings` to silence a rule unless the user explicitly asks for it

6. After all fixes, run the compile and build one final time to confirm a clean result.
