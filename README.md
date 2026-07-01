# ghcp-java-intellij

**A GitHub Copilot tutorial/workshop/lab focused on IntelliJ IDEA and Spec-Driven Development (SDD).**

This is a Java and IntelliJ IDEA adaptation of the [ghcp-advanced](https://github.com/jkordick/ghcp-advanced) workshop.

👉 **[Open workshop on moaw.dev](https://aka.ms/ws?src=gh:jkordick/ghcp-java-intellij/main/docs/)**

> Or read the raw markdown: [`docs/workshop.md`](docs/workshop.md)

## What you'll learn

1. **Getting started with GitHub Copilot in IntelliJ IDEA**: completions, Chat, custom agents, the Copilot CLI, custom instructions, prompt files, skills and MCP servers.
2. **Spec-Driven Development (SDD)**: Make specifications, not vibes, drive what Copilot builds. End-to-end Java / Spring Boot example.
3. **spec-kit**: an open-source toolkit by the GitHub team that formalizes and extends the SDD loop you did by hand.

## Tech stack

| Layer | Choice |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.x (Spring MVC) |
| Build | Maven |
| Tests | JUnit 5 + Mockito |
| Database | H2 (dev) / SQLite via JDBC (persistence) |
| IDE | IntelliJ IDEA |

## Pre-reqs

- A GitHub account with Copilot access (Free, Pro, Business or Enterprise)
- IntelliJ IDEA (Community or Ultimate) with the **GitHub Copilot** plugin
- Java 21 JDK
- Maven 3.9+
- [spec-kit](https://github.com/github/spec-kit)

See [the Pre-requisites section of the workshop](docs/workshop.md#pre-requisites) for more details.

## License

MIT

## Credits

Based on [ghcp-advanced](https://github.com/jkordick/ghcp-advanced) by Julia Kordick, itself inspired by the excellent [GitHub Copilot HoL by @Philess](https://moaw.dev/workshop/gh:Philess/GHCopilotHoL/main/docs/).
