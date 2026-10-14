# GEN-Z Android Apps

Four separate Android APKs:

- `app-kisan`: farmer registration, crop listings and purchase requests.
- `app-owner`: owner purchase dashboard, requests, accounts, reports and delivery rate settings.
- `app-delivery`: delivery assignments, status updates and consent-based location sharing.
- `app-seo-fixer`: offline Creator SEO Fixer. Accepts a YouTube URL plus manually entered title, description and tags; runs local rule-based checks; creates copyable suggestions. It uses no external API, Firebase, login or network permission.

## Creator SEO Fixer limitations

Without a YouTube API or another data source, the SEO Fixer cannot automatically retrieve video metadata, hidden tags, channel analytics, CTR, watch time or subscriber data. The URL check validates its format only. Its checklist score is a local heuristic, not a YouTube ranking score. It does not generate videos or publish/edit videos on YouTube.

## Delivery pricing

Owner configures a rate from ₹10 to ₹20 per km. The app estimates the delivery charge from the route distance. The rate and quote must be stored with each assigned delivery so later rate changes do not silently alter existing assignments. Delivery Boy cannot edit the rate.

## Build

Open the repository in Android Studio or run the GitHub Actions workflow. The workflow builds debug APKs and uploads them as artifacts. Firebase configuration, secure database rules, Maps routing, FCM, location tracking and production testing for the GEN-Z farming modules must be completed before real-world use. Do not add Firebase service-account credentials to source control.
