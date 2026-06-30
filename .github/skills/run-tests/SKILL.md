---
name: run-tests
description: Use when the user asks to run tests, verify changes, check if code works, or validate that nothing is broken. Also use after making code changes that could affect existing functionality.
---

1. Run the full test suite using the terminal:

   ```bash
   mvn test
   ```

2. Read the test output carefully. Pay attention to:
   - Number of tests passed vs failed
   - Stack traces and assertion errors
   - Which test class and method failed

3. If all tests pass, confirm the result and summarize what was validated.

4. If any tests fail:
   - Identify the root cause from the error message and stack trace
   - Fix the code (not the test) unless the test itself is clearly wrong
   - Run `mvn test` again to verify the fix
   - Repeat until all tests pass

5. Never skip a failing test. Never mark a test with `@Disabled` to make the suite pass.
