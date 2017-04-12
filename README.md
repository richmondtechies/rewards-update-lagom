
#set up local postgres db instance

db.default {
  driver = "org.postgresql.Driver"
  url = "jdbc:postgresql://127.0.0.1:5433/rewards"
  username = postgres
  password = admin
}
Refer: application.conf

#To build and run this application:

  1. navigate to rewards-update-lagom folder
  2. execute: ./rewards-update/start.sh
  3. Hit http://localhost:11400/api/card/offer with below param:
    {
    "name": "Raja",
    "ssn":"1234",
    "dob":"03-11-2001"
    }
