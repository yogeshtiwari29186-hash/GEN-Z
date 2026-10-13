# GEN-Z Android Apps

Three separate Android APKs sharing Firebase:

- `app-kisan`: farmer registration, crop listings and purchase requests.
- `app-owner`: owner purchase dashboard, requests, accounts, reports and delivery rate settings.
- `app-delivery`: delivery assignments, status updates and consent-based location sharing.

## Delivery pricing
Owner configures a rate from ₹10 to ₹20 per km. The app estimates the delivery charge from the route distance. The rate and quote must be stored with each assigned delivery so later rate changes do not silently alter existing assignments. Delivery Boy cannot edit the rate.

## Current status
Starter scaffold only. Firebase configuration, secure database rules, Maps routing, FCM, location tracking and production testing must be completed before real-world use. Do not add Firebase service-account credentials to source control.

## Build
Open the repository in Android Studio or run the GitHub Actions workflow. Add Firebase Android configuration files/secrets as documented in the workflow before expecting a Firebase-connected build.
