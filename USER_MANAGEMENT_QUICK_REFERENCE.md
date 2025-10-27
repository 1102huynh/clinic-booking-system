# 👥 User Management - Quick Reference Guide

## URL Endpoints

```
List Users:         GET  /admin/users
Add User Form:      GET  /admin/users/add
Create User:        POST /admin/users/add
View User:          GET  /admin/users/{userId}
Edit User Form:     GET  /admin/users/{userId}/edit
Update User:        POST /admin/users/{userId}/edit
Delete User:        POST /admin/users/{userId}/delete
Toggle Status:      POST /admin/users/{userId}/toggle-status
```

---

## 🎯 Quick Actions

### Add a New User
```
1. Navigate to /admin/users
2. Click "Add New User" button
3. Fill in form fields
4. Click "Create User"
```

### View User Details
```
1. From user list, click "View" button
2. See all user information
3. Can edit or delete from here
```

### Edit User
```
1. Click "Edit" button (on list or detail page)
2. Modify fields (name, email, role)
3. Click "Update User"
```

### Delete User
```
1. Click "Delete" button (red)
2. Confirm in dialog
3. User removed from system
```

### Disable User
```
1. Go to user detail page
2. Click "Disable User" button
3. User cannot login anymore
```

### Enable User
```
1. Go to user detail page
2. Click "Enable User" button
3. User can now login
```

---

## 📋 Form Fields

### Add User Form
| Field | Type | Required | Rules |
|-------|------|----------|-------|
| Username | Text | Yes | Unique, min 1 char |
| Full Name | Text | Yes | Any characters |
| Email | Email | Yes | Valid email format |
| Password | Password | Yes | Min 6 characters |
| Role | Select | Yes | Admin, Doctor, Patient |

### Edit User Form
| Field | Type | Required | Rules |
|-------|------|----------|-------|
| Full Name | Text | Yes | Any characters |
| Email | Email | Yes | Valid email format |
| Role | Select | Yes | Admin, Doctor, Patient |
| Enabled | Checkbox | No | Checked = Active |

---

## 🎨 Button Colors & Meanings

| Button | Color | Action |
|--------|-------|--------|
| Add New User | Green | Create new user |
| View | Blue | Show user details |
| Edit | Orange | Modify user info |
| Delete | Red | Remove user |
| Enable | Teal | Activate account |
| Disable | Teal | Deactivate account |

---

## 👥 Role Types

| Role | Privileges | Use Case |
|------|-----------|----------|
| **Admin** | Full system access | System administrator |
| **Doctor** | Medical operations | Healthcare provider |
| **Patient** | Book appointments | Patient |

---

## 🔐 Status Types

| Status | Meaning | Login |
|--------|---------|-------|
| **Active** | User enabled | ✅ Can login |
| **Inactive** | User disabled | ❌ Cannot login |

---

## 📊 User List View

### Columns
- Username
- Full Name
- Email
- Role (with color badge)
- Status (with color badge)
- Actions (View, Edit, Delete)

### Features
- Add button in header
- Color-coded badges
- Responsive table
- Empty state message

---

## 📄 User Detail View

### Sections
1. **User Information**
   - Username
   - Full Name
   - Email
   - Role

2. **Account Status**
   - Status indicator
   
3. **Action Buttons**
   - Back to Users
   - Edit User
   - Delete User
   - Enable/Disable User

---

## ⚡ Quick Tips

✅ **Username is permanent** - Cannot change after creation
✅ **Password hashing** - Passwords are bcrypt hashed
✅ **Unique usernames** - System prevents duplicate usernames
✅ **Delete is permanent** - Deleted users cannot be recovered
✅ **Enable/Disable is temporary** - User data preserved when disabled
✅ **All fields required** - Cannot leave required fields empty

---

## 🚨 Important Notes

⚠️ **Delete is permanent** - Make sure before confirming
⚠️ **Unique username** - Each user must have different username
⚠️ **Password forgotten** - No password reset, only admin can delete and recreate
⚠️ **Role cannot be changed** - May need to delete and recreate if wrong role

---

## 📱 Mobile Compatibility

✅ All pages are responsive
✅ Works on phone, tablet, desktop
✅ Buttons stack vertically on mobile
✅ Forms easy to fill on mobile

---

## 🔒 Security Features

✅ Password hashing with BCrypt
✅ Unique username requirement
✅ CSRF protection on forms
✅ Input validation on server
✅ Admin-only access
✅ Confirmation on delete

---

## 💾 Examples

### Creating a Doctor User
```
Username: dr_smith
Full Name: Dr. John Smith
Email: john.smith@clinic.com
Password: secure123456
Role: Doctor
Status: Active ✓
```

### Creating an Admin User
```
Username: admin_jane
Full Name: Jane Doe
Email: jane.admin@clinic.com
Password: adminpass123
Role: Admin
Status: Active ✓
```

### Creating a Patient User
```
Username: patient_bob
Full Name: Bob Johnson
Email: bob@email.com
Password: patient123
Role: Patient
Status: Active ✓
```

---

## 🆘 Troubleshooting

**"Username already exists"**
- Username is taken by another user
- Choose a different username

**"Password must be at least 6 characters"**
- Password is too short
- Enter a password with 6+ characters

**"User not found"**
- User has been deleted
- Go back and refresh

**"Email invalid"**
- Email format incorrect
- Use valid email format (example@domain.com)

---

## 📞 Common Tasks

### Change User Role
1. Click Edit
2. Change Role dropdown
3. Click Update

### Change User Name
1. Click Edit
2. Modify Full Name
3. Click Update

### Temporarily Disable User
1. Go to user detail
2. Click Disable User
3. User cannot login until enabled

### Permanently Remove User
1. Click Delete (red button)
2. Confirm in dialog
3. User is deleted

---

**Version:** 1.0  
**Last Updated:** October 27, 2025  
**Status:** ✅ READY FOR USE

