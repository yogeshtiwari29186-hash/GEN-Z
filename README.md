# Creator SEO Fixer

A standalone Android app for offline, rule-based YouTube metadata checks.

## Features
- Enter a YouTube video or channel URL (format validation only).
- Manually enter title, description and comma-separated tags.
- Run local SEO checklist rules and review suggestions.
- Copy suggested title, description and tag ideas.
- No external API, Firebase, login, video generation or network permission.

## Limitations
Without an official YouTube API or another data source, the app cannot automatically retrieve video metadata, hidden tags, channel analytics, CTR, watch time or subscriber data. The score is a local checklist heuristic, not a YouTube ranking score. It cannot publish or edit videos on YouTube.

## Build
Open the project in Android Studio or use GitHub Actions. The workflow builds a debug APK and uploads it as the `CreatorSEO-Fixer-debug` artifact.
