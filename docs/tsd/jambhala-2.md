
title: Technical Specs
created at: Sat Oct 22 2022 12:33:17 GMT+0000 (Coordinated Universal Time)
updated at: Sat Oct 22 2022 12:33:17 GMT+0000 (Coordinated Universal Time)
---

# Tech Specs for [JAMBHALA-2](https://zakiyunus.atlassian.net/browse/JAMBHALA-2)

## Background & research

Normally, a rimaubank customer also has close family members or friends who are also rimaubank customer. This function allows the said rimaubank customer to send money to their family or friends. Extensive usage of this function means that funds stay and increase within maybank deposit accounts, maintaining or lowering the bank's cost of funds


## Problem statement

As a rimaubank customer, I would like to do funds transfer from my rimaubank account to another rimaubank account so I can send money to my friends or family

## Goals

-   List goals
    - Customer should need at most 4 steps to transfer money
-   KPIs
    - 1000 intrabank funds transfer between rimaubank accounts per month
    - Total value of IDR 1Mio of funds exchanged between rimaubank accounts per month

## ﻿Hypothesis

If we make it frictionless for customers to transfer money online between rimaubank accounts, customers are more likely to recommend their friends and family to open or maintain existing rimaubank account.

## **Technical solution**

1. Login should produce an Oauth Token that can be used to call the remaining API's
2. There should be 4 main API's involved
````
   - GET /accounts/<account-number>/balance-inquiry
   
   - GET /accounts/<account-number>/holder-name
   
   - POST /transfer/intra-bank
   
   - GET /push-notifications/new
   
   - GET /transaction-history/<account-number>
````

## Scope

Explain the solution is, how it works and the extent of the work involved.

1.  Requirements > See [JAMBHALA-2](https://zakiyunus.atlassian.net/browse/JAMBHALA-2)
    - The customer must have a valid rimaubank online banking credentials
    - The source account must have sufficient funds to transfer the money
    - The recepient account must be a valid rimaubank account
    - Upon successful transfer, the sender will receive a notification saying that the transfer to recipient is successful with amount and date
    - When the sender checks their transaction history, the transfer activity must be reflected
2.  Future evolutions
    - There must be a 2nd factor authentication to protect the sender from fraudsters
    - There should be an option for customer to repeat the same transaction
3.  Out of scope
    - Email notification

## Designs and assets

Add any necessary figma project link, hi-fi mockups, SVGs, font files, images, prototypes...

## Open questions

-   [ ] Should we notify the recipient of the incoming transfer?

          