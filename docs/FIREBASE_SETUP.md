# Firebase integration checklist

This repository currently contains UI/build scaffolding only. Before real users or production data:

1. Create a Firebase project and register Android apps with package IDs in.genz.kisan, in.genz.owner, and in.genz.delivery.
2. Add each app's google-services.json locally; never commit service-account keys. Add Firebase Auth, database and FCM dependencies.
3. Enforce roles with trusted server-managed claims or role records. Never trust a client-supplied owner role.
4. Restrict database rules: farmers can access only their own profile and requests; delivery staff can access only assigned tasks; owner-only price and finance writes must be enforced by rules/server.
5. Store the owner's per-km rate in a protected settings path and constrain it to ₹10–₹20. When assigning a delivery, save ratePerKmSnapshot, route distance and quoted charge on that assignment so later rate changes do not rewrite old assignments.
6. Calculate route distance using a real routing provider; straight-line distance is not road distance. Clearly distinguish estimates from final charges.
7. Send FCM notifications through trusted server/Cloud Functions; do not put service-account keys in APKs.
8. Implement consent-based foreground location sharing with a visible notification. Stop sharing when delivery completes or the worker turns it off.
9. Test security rules with Firebase Emulator Suite and test on real Android devices before launch.
