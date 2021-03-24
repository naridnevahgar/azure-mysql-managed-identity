# Demo for Azure MySQL access from Java with Managed Identity (VM)

- Create a new Azure SQL for MySQL database
- Assign an Active Directory admin for the MySQL database
- Create a new Azure VM and assign identity (system assigned) to the VM
- Create a new user in MySQL database for the VM
```
create aaduser vmuser identified by '<managed identity's object id>'
```