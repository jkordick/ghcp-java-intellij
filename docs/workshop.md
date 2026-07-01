---
published: true
type: workshop
title: "GitHub Copilot Advanced: Spec-Driven Development and beyond (Java / IntelliJ edition)"
short_title: "GitHub Copilot Advanced: SDD and beyond — Java"
description: Learn the fundamentals of GitHub Copilot in IntelliJ IDEA and then go deep on Spec-Driven Development (SDD), the discipline of letting specifications drive your AI-assisted Java code.
level: intermediate
authors:
  - Julia Kordick
  - Artur Speth
contacts:
  - "@jkordick"
  - "@aspeth"
duration_minutes: 240
tags: GitHub, Copilot, AI, Spec-Driven Development, SDD, Java, Spring Boot, Maven, IntelliJ
navigation_levels: 3
navigation_numbering: true
---

# GitHub Copilot Advanced: Spec-Driven Development and beyond (Java / IntelliJ)

*Version 1.0 — June 2026 · Java / IntelliJ edition*

Welcome! This workshop is a Java / IntelliJ IDEA adaptation of the [ghcp-advanced](https://github.com/jkordick/ghcp-advanced) workshop. It has the following parts:

1. **Getting Started with GitHub Copilot**: a broad chapter for anyone who has never (or barely) used GitHub Copilot in IntelliJ IDEA. Covers inline completions, Chat, Agent mode, the Copilot CLI, custom instructions, prompt files, skills and MCP servers.
2. **Spec-Driven Development (SDD)**: Learn how to make specifications drive what GitHub Copilot builds for you — end-to-end on a Java / Spring Boot feature.
3. **Introduction of [spec-kit](https://github.com/github/spec-kit)** as a tool to use spec-driven development conveniently with most agentic coding tools.

<div class="info" data-title="Who is this for?">

> Developers, tech leads and architects who want to bring their agentic AI and GitHub Copilot skills to the next level using **Java and IntelliJ IDEA**. No prior Copilot experience is required, but recommended. Chapter 1 is designed to help you catch up fast.

</div>

<div class="warning" data-title="Heads up">

> GitHub Copilot evolves quickly. UI labels, menu locations and feature names may shift between releases. If something looks slightly different in your IntelliJ IDEA, search the **Find Action** dialog (`Cmd/Ctrl+Shift+A`) — the feature is almost certainly still there.

</div>

---

## Pre-requisites

You need the following before starting:

|                                     |                                                                                          |
| ----------------------------------- | ---------------------------------------------------------------------------------------- |
| A GitHub account                    | [Create free GitHub account](https://github.com/join)                                   |
| GitHub Copilot access               | Free, Pro, Business or Enterprise — see below                                            |
| IntelliJ IDEA                       | Community or Ultimate — [Download](https://www.jetbrains.com/idea/download/)             |
| GitHub Copilot plugin               | Install from JetBrains Marketplace (search "GitHub Copilot")                            |
| Java 21 JDK                         | [Download Temurin 21](https://adoptium.net/) or use IntelliJ's bundled JDK manager      |
| Maven 3.9+                          | [Install](https://maven.apache.org/install.html) or use IntelliJ's bundled Maven        |
| GitHub CLI                          | [Install](https://cli.github.com/)                                                       |
| GitHub Copilot CLI                  | [Install](https://github.com/github/copilot-cli)                                         |
| A terminal                          | Any modern shell (bash, zsh, pwsh)                                                       |
| A fork of this repo                 | [Fork `jkordick/ghcp-java-intellij`](https://github.com/jkordick/ghcp-java-intellij/fork) and clone your fork locally |

### Getting Copilot access

- **Individual Free/Pro:** sign up at [github.com/github-copilot/signup](https://github.com/github-copilot/signup).
- **Through your organization:** request access at [github.com/settings/copilot](https://github.com/settings/copilot).

<div class="info" data-title="Enterprise organization check">

> Some features in this workshop (Agent mode, MCP, the GitHub Copilot CLI, spec-kit) may be restricted by your organization's policy. The workshop is structured so that each section is **independently useful**; skip what you cannot use.

</div>

---

# Chapter 1 — Getting Started with GitHub Copilot

> If you have never (or only barely) used GitHub Copilot in IntelliJ IDEA, **start here**. This chapter is intentionally broad. Skip (parts of) it if you are already familiar and comfortable.

## 1.1 Copilot in IntelliJ IDEA

### Install the plugin

Open IntelliJ IDEA and go to **Settings / Preferences → Plugins → Marketplace**, search for **GitHub Copilot** and install it. Restart the IDE. Sign in with your GitHub account via **Tools → GitHub Copilot → Sign In**.

You will use four surfaces:

### Inline completions

Start typing in any Java file and Copilot suggests grey inline completions. Accept with `Tab`, dismiss with `Esc`, cycle alternatives with `Alt+]` / `Alt+[`.

> Tip: Check your configured shortcuts under **Settings → Keymap** and search for "Copilot".

**Try it.** Create `Fibonacci.java` and type:

```java
// Returns the nth Fibonacci number
public int fib(int n) {
```

Let Copilot complete the method body. Now add a second comment `// Returns the nth prime` above a new method and watch how *prior context in the file* guides the suggestion.

### Inline chat (`Alt+\` or right-click → Ask Copilot)

Select code, press `Alt+\` (or right-click → **Ask GitHub Copilot**), and ask for a transformation: *"add input validation and Javadoc"*, *"convert to use Stream API"*, *"write a unit test for this"*.

### Chat panel

Open the Copilot Chat panel from the right-hand sidebar or via **Tools → GitHub Copilot → Open Chat**. Use `#` to attach context (files, symbols) and ask questions or request changes across files.

```text
Explain why the checkout service throws on an empty cart
```

> **In the Copilot CLI:** attach files with `@` and just ask in plain language. Slash commands in the CLI are for the session itself (`/agent`, `/mcp`, `/context`, `/compact`, `/usage`, `/resume`, `/cwd`, `/add-dir`). Prefix a line with `!` to run a raw shell command without invoking the model.

### Agent mode

In the Chat panel, switch to **Agent** mode. Agent mode can read, create and edit files across your project, run commands (with your approval) and iterate until a task is done. This is the surface you will lean on most in Chapter 2.

<div class="tip" data-title="Pick the right mode">

> - **Ask / Chat** — questions, explanations, information gathering
> - **Agent** — autonomous task execution, coding, execution

</div>

> **In the Copilot CLI:** there is no Ask/Agent toggle — the CLI is always agentic. Cycle into *plan mode* with `Shift+Tab` to design before changes are made.

## 1.2 GitHub Copilot CLI

GitHub Copilot is IDE-independent and available via the CLI. It gives you an agent in your terminal.

**Try it.** After installation, open a terminal, navigate to any folder or repo, run `copilot --banner` and ask "what is this repository about?"

<div class="warning" data-title="Command line tool execution">

> Always review suggested shell commands before accepting them. **Never blindly execute** suggested commands.

</div>

## 1.3 copilot-instructions.md & AGENTS.md

GitHub Copilot reads project-level instructions from `.github/copilot-instructions.md`. There is always only one `copilot-instructions.md` per repository, located exactly at `.github/`. You can create more specific instructions in `.github/instructions/*.instructions.md` (e.g. a `testing.instructions.md`); scoped files use a frontmatter `applyTo` glob to limit themselves to matching paths.

GHCP also reads `AGENTS.md` files — you can have multiple, merged from the repository root downwards. The file must always be named exactly `AGENTS.md`.

Two things to keep in mind:
1. Information in them should be relevant in *every* context the agent operates in.
2. Write them so they are verifiable. Avoid vague statements like *"write good code"*. Stick to rules that can be checked (e.g., *"tests must be runnable via `mvn test`"*); plus concrete paths the agent should or should not touch.

> **In IntelliJ:** both `copilot-instructions.md` and `AGENTS.md` are picked up automatically when you use Copilot Chat or Agent mode inside the project.

## 1.4 Prompt files

Prompt files (`*.prompt.md`) are **reusable prompts** stored in your repo. In VS Code they show up in Chat as runnable commands (`/add-test`). In IntelliJ, you can reference them directly in Chat by pasting their content, or use them from the **Copilot CLI** by piping them into a session.

Create `.github/prompts/add-test.prompt.md`:

```markdown
---
mode: agent
description: Add unit tests
---
Look at the source I provide. Write unit tests that cover the main exported
methods. Place tests in `src/test/java/` mirroring the source package,
named `<ClassName>Test.java`, and run them with `mvn test`.
```

<div class="tip" data-title="IntelliJ + Prompt files">

> As of mid-2026, IntelliJ's Copilot integration runs prompt files when you type `/` in the Agent chat. If this is not yet available in your version, use the Copilot CLI: `copilot --prompt @.github/prompts/add-test.prompt.md`.

</div>

> **In the Copilot CLI:** `.prompt.md` files are not exposed as slash commands in the CLI. For reusable workflows, use custom agents (1.5) or skills (1.6), or paste the prompt body into your session.

## 1.5 Custom agents

Custom agents (`*.agent.md`) let you create specialized Copilot personas with tailored expertise, tool access and instructions. Store them in `.github/agents/` at the repo level. They are available in VS Code, on github.com and in the Copilot CLI.

Create `.github/agents/test-specialist.agent.md`:

```markdown
---
name: test-specialist
description: Focuses on test coverage and quality without modifying production code
tools: ["codebase", "search", "editFiles", "runCommands"]
---
You are a testing specialist focused on improving code quality through comprehensive testing.
Analyze existing tests, identify coverage gaps, and write JUnit 5 + Mockito tests.
Focus only on test files — avoid modifying production code unless specifically requested.
```

Pick the agent from the mode dropdown in IntelliJ Chat, or use it in the Copilot CLI with `/agent`. See [Creating custom agents](https://docs.github.com/en/copilot/how-tos/copilot-on-github/customize-copilot/customize-cloud-agent/create-custom-agents) for full configuration options.

> **In the Copilot CLI:** same files (`.github/agents/` for the repo, `~/.copilot/agents/` for personal). Launch directly: `copilot --agent=test-specialist --prompt "…"`.

## 1.6 Agent skills

Skills live in `.github/skills/` (project) or `~/.copilot/skills/` (personal). This repo ships with two example skills:

| Skill | What it does |
| --- | --- |
| `run-tests` | Runs `mvn test`, reads failures, fixes the code (not the test), re-runs until green. |
| `build-and-compile` | Runs `mvn compile` then `mvn package -DskipTests` and optionally Checkstyle, fixes issues in the right order. |

Open `.github/skills/run-tests/SKILL.md` to see a minimal example.

Copilot auto-loads a matching skill when it detects a relevant task, or you invoke it explicitly with `/run-tests`. Skills are an [open standard](https://agentskills.io/) — they work across IntelliJ, VS Code, the Copilot CLI and any other agentic AI.

## 1.7 MCP servers

The [Model Context Protocol](https://modelcontextprotocol.io) lets GitHub Copilot connect to external tools — issue trackers, databases, APIs.

In IntelliJ, configure MCP servers in **Settings → Tools → GitHub Copilot → MCP Servers** or via `.vscode/mcp.json` (also picked up in some IntelliJ versions):

```json
{
  "servers": {
    "github": {
      "type": "http",
      "url": "https://api.githubcopilot.com/mcp/"
    }
  }
}
```

> **In the Copilot CLI:** the GitHub MCP server is preconfigured. Add more with `/mcp add`. Server definitions live in `~/.copilot/mcp-config.json`.

<div class="info" data-title="Enterprise organization check">

> MCP servers may be governed by allow-lists. Check with your platform engineering team before trying to add new ones.

</div>

## 1.8 Quick mental model

| Surface                        | Use for                                          |
| ------------------------------ | ------------------------------------------------ |
| Inline completion              | Local, line-level help                           |
| Inline chat (`Alt+\`)          | Targeted edits to a selection                    |
| Chat panel (Ask)               | Questions, explanations                          |
| Chat panel (Agent)             | Autonomous tasks — the SDD workhorse             |
| Copilot CLI                    | Same power, in the terminal / CI                 |
| `copilot-instructions.md` / `AGENTS.md` | Durable, project-wide rules             |
| Prompt files                   | Reusable, parameterizable workflows              |
| Custom agents                  | Specialized personas + tool scoping              |
| Agent skills                   | Packaged multi-step capabilities, on demand      |
| MCP servers                    | Real-world tools and data the agent can use      |

You now have the full toolbox. The rest of the workshop is about **using it**.

---

# Chapter 2 — Spec-Driven Development

## 2.1 Why spec-driven?

A lot of people use agentic coding tools like this:

> *"build me a REST API for managing tasks"*

…and then spend the next hour wrestling with what the agent assumed. This is **vibe coding**: you ship intent, the agent ships interpretation, and the gap between them becomes technical debt and security risks.

**Spec-Driven Development (SDD)** tightly structures the agentic workflow:

```
Spec  →  Plan  →  Tasks  →  Implement
```

The spec describes the **what**, derived from the user stories. The plan describes the technical **how**. The tasks are a todo list based on the spec and the plan. In the implementation, the task list is executed.

You gain three things:

1. **Reviewability.** A precise spec & plan is something a human (or a second AI agent) can review. 800 lines of generated Java distributed across controllers, services and repositories is not.
2. **Control.** The 3 planning steps create a "contract" between your intent and the execution by an agentic AI.
3. **Better outcomes.** With divide and conquer, you can tackle more complex, multi-file, multi-iteration features that are still manageable and maintainable.

<div class="tip" data-title="Important">

> If you struggle to break down the task at hand into a reasonably sized spec, you probably need to break the task into smaller pieces. SDD is not a silver bullet for complexity — it is a discipline that helps you manage it, but it does not replace good judgment in scope definition.

</div>

## 2.2 Hands-on: build The Rubber Duck Emporium with SDD

You will build a small e-commerce application for **The Rubber Duck Emporium** — a shop that sells specialty rubber ducks for every possible occasion: Debugging Ducks, Philosopher Ducks, Maritime Ducks, Wellness Ducks, and Limited Editions.

The user stories live in the [`user-stories/`](../user-stories/) folder of this repo. **Read [`user-stories/README.md`](../user-stories/README.md) first** — it describes the product, personas (Quincy Quacker the customer, Dr. Mallard the curator), shared constraints, and the dependency graph between stories.

There are 9 stories. A realistic ~90–120 minute run completes the full application.

### 2.2.1 Scaffold

**Option A — IntelliJ Spring Initializr (recommended)**

1. Open IntelliJ IDEA and choose **File → New → Project**.
2. Select **Spring Initializr**.
3. Configure:
   - **Language:** Java
   - **Type:** Maven
   - **Group:** `com.duckemporium`
   - **Artifact:** `duck-emporium`
   - **Java:** 21
4. Add dependencies: **Spring Web**, **Spring Data JPA**, **H2 Database**.
5. Click **Create**. IntelliJ opens the project directly.
6. Create a `specs/` folder at the project root.

**Option B — Spring Initializr CLI**

```bash
curl https://start.spring.io/starter.zip \
  -d dependencies=web,data-jpa,h2 \
  -d language=java \
  -d javaVersion=21 \
  -d artifactId=duck-emporium \
  -d groupId=com.duckemporium \
  -d name=duck-emporium \
  -o duck-emporium.zip
unzip duck-emporium.zip -d duck-emporium
cd duck-emporium
mkdir specs
git init && git add -A && git commit -m "scaffold project"
```

Open the `duck-emporium/` folder in IntelliJ.

Add a minimal `AGENTS.md` in the `duck-emporium/` folder:

```markdown
# Project: duck-emporium

- Language: Java 21. Framework: Spring Boot 3.x (Spring MVC). Build: Maven.
- Use Spring Data JPA with H2 in-memory database (dev) or SQLite via JDBC (persistence).
- Tests live in `src/test/java/` as `*Test.java`. Run with `mvn test`.
- Use JUnit 5 + Mockito. Use `MockMvc` for controller (slice) tests.
- User stories live in `../user-stories/`. Specs go in `specs/<story-id>/`.
- Never edit `user-stories/**`.
- Only edit files under `specs/**` when invoked through an `sdd-*` prompt.
- Follow the workflow in `.github/prompts/sdd-*.prompt.md`.
- Payments are MOCKED. Never integrate a real payment provider.
```

### 2.2.2 Add the SDD prompt files

All prompts assume you run them from inside the `duck-emporium/` folder. They use a `${input:storyId}` placeholder — IntelliJ (or the CLI) will prompt you for the story ID when you invoke them.

<div class="tip" data-title="Remember!">

> Create prompt files manually or via **Tools → GitHub Copilot → New Prompt File…** and copy-paste the content below.

</div>

Create `.github/prompts/sdd-spec.prompt.md`:

```markdown
---
name: sdd-spec
description: Turn a user story into a reviewable spec.
---
Goal: produce `specs/${input:storyId}/spec.md` from `../user-stories/${input:storyId}.md`.

Rules:
- Read the user story file first. Treat it as raw input, not as a spec.
- Ask clarifying questions ONE at a time. Use the "Open questions" section of the
  user story as a starting point but go beyond it.
- Do not write code or any file other than `specs/${input:storyId}/spec.md`.
- When you have enough information, write the spec using this outline:
  Problem, Users, Scope (in/out), Functional requirements,
  Non-functional requirements, Acceptance criteria, Open questions.
- Stop after writing the spec and wait for approval.
```

Create `.github/prompts/sdd-plan.prompt.md`:

```markdown
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
```

Create `.github/prompts/sdd-tasks.prompt.md`:

```markdown
---
description: Break an approved plan into an ordered task list.
---
Goal: produce `specs/${input:storyId}/tasks.md` from `specs/${input:storyId}/plan.md`.

Rules:
- Each task is independently committable and has a clear acceptance check
  (e.g. "`mvn test` for XxxTest passes").
- Number tasks sequentially. Note dependencies between them.
- Do not write code. Do not modify the spec or the plan.
- Stop after writing tasks.md and wait for approval.
```

Create `.github/prompts/sdd-implement.prompt.md`:

```markdown
---
description: Implement a single numbered task from the task list.
---
Goal: implement task `${input:taskNumber}` from `specs/${input:storyId}/tasks.md`.

Rules:
- Read spec.md, plan.md and tasks.md before touching any code.
- Implement ONLY the named task. Do not start the next one.
- Add or update tests for the task. Run `mvn test` and ensure it is green.
- If a test fails, fix the code (not the test) until it passes.
- Stop after the task is green and wait for approval to commit.
```

### 2.2.3 Run the loop, story by story

Pick story 1 (`browse-catalog`). In IntelliJ Chat (Agent mode), invoke the prompt:

```text
/sdd-spec
```

When prompted, enter `browse-catalog`. Answer the clarifying questions. Expect to make foundational decisions that will affect every later story — e.g., *"what fields does a `Duck` entity have?"*, *"should categories be a separate table or an enum?"*. Review `specs/browse-catalog/spec.md`. Iterate until you are happy.

Then run the next prompts in the loop (each will prompt for `storyId`, and `sdd-implement` will also ask for `taskNumber`):

```text
/sdd-plan
/sdd-tasks
/sdd-implement
```

<div class="tip" data-title="Tip">

> **Commit after every passing task.** Keep the IntelliJ Maven tool window open — run `mvn test` directly from there after each implementation step.

</div>

When the story is done, move on to story 2 (`duck-detail`), which builds on story 1's foundation.

<div class="tip" data-title="Tip">

> Spec-driven development can potentially take all user stories at once, but we recommend doing it one story at a time for the first few iterations. When you're ready for the all-in-one experience, check out the spec-kit chapter.

</div>

### 2.2.4 What you should notice

- During story 1's spec, you make decisions (Duck entity shape, database schema, HTTP contract) that *prevent* whole categories of confusion in stories 2–9.
- The agent stops asking *"what did you mean by…?"* mid-implementation, because you front-loaded that in the spec.
- When a test fails, the fix is local to one task, not a sprawling rewrite across the service layer.
- If you change your mind, you edit the spec and re-run from `/sdd-plan`. The plan, tasks and code regenerate cleanly.
- A teammate can review `spec.md` + `tasks.md` in 5 minutes and know exactly what shipped.
- The user story files are *never* edited by the agent. They are the immutable source of truth for "what the customer asked for".

<div class="tip" data-title="Do this now">

> Even outside the workshop, try this on the next ticket in your real backlog: copy the ticket text into a `user-stories/` file, then run `/sdd-spec` against it. Time-box to 20 minutes. The clarity gain is the win. Code is a bonus.

</div>

## 2.3 SDD in the Copilot CLI

Everything above works in the GitHub Copilot CLI too. Same prompt files, same instructions — just invoked from `copilot` in your terminal:

```bash
# From inside duck-emporium/
copilot --prompt @../.github/prompts/sdd-spec.prompt.md
# When asked for storyId, enter: browse-catalog
```

This is particularly useful in CI pipelines, or when you prefer working from a terminal.

---

# Chapter 3 — spec-kit by GitHub

[spec-kit](https://github.com/github/spec-kit) is an open-source toolkit by the GitHub team that formalizes and extends the loop you just did by hand. As it is an open-source project it can be used not only with GitHub Copilot but [many more agentic AIs for coding](https://github.github.io/spec-kit/reference/integrations.html). So if you now want to give it a try with Claude, Cursor or Codex, this is the moment.

<div class="info" data-title="If you want to learn more">

> https://developer.microsoft.com/blog/spec-driven-development-ai-native-engineering

</div>

It ships a `specify` CLI that scaffolds the `specs/` layout, prompt files and chatmodes for you, and integrates with several AI agents. The workflow is **language-agnostic** — it works equally well for Java/Maven projects.

<div class="warning" data-title="Enterprise organization check">

> Some organizations restrict which CLIs developers can install (`uv`, `uvx`, `npx`, etc.). If `specify` is not allowed in your environment, you have already learned the underlying workflow in Chapter 2.

</div>

## 3.1 Install & initialize the project

Follow the installation instructions in the [spec-kit README.md](https://github.com/github/spec-kit#-get-started).

In the root of the project:

```bash
specify init duck-emporium --integration copilot
# choose between bash or powershell
cd duck-emporium
```

<div class="warning" data-title="Workaround">

> `specify init duck-emporium` creates a nested layout (a `duck-emporium/` folder containing `.specify/`, `.github/`, scripts, etc.). For the agent to pick up the slash commands in this setup, cut those folders out of `duck-emporium/` and paste them at the repository root. Alternatively, run `specify init .` from inside an empty target folder to avoid the nesting entirely.

</div>

This creates a structured layout with the SDD phases (`/speckit.specify`, `/speckit.plan`, `/speckit.tasks`, `/speckit.implement` and some additional steps) pre-wired as slash commands.

## 3.2 Run the same loop, but guided

In IntelliJ Chat (Agent mode) or in the Copilot CLI:

```text
/speckit.specify checkout #user-stories and create a specification out of them
```

<div class="tip" data-title="Branching">

> When spec-kit starts working it creates a dedicated working branch automatically.

</div>

Compare what spec-kit generates against the hand-rolled prompts from Chapter 2. You will recognize every step — spec-kit just removes the boilerplate and adds a bit of structure. The output (`spec.md`, `plan.md`, `tasks.md`) is identical in shape to what you wrote by hand.

*That's it for this workshop edition. Happy spec-driven coding — and may your rubber ducks always have opinions about your code.* 🦆
