# Git Setup Instructions for Clinic Booking System

## Initial Git Configuration

### 1. Configure Git User (First Time Only)

Open Git Bash or your IDE terminal and run:

```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

Or for this project only:
```bash
cd D:\learn\clinic-booking-system
git config user.name "Your Name"
git config user.email "your.email@example.com"
```

## Initialize Repository

```bash
cd D:\learn\clinic-booking-system
git init
```

## Create and Switch to Develop Branch

### Option 1: Create develop branch from main

```bash
# Create the develop branch
git checkout -b develop

# This creates a new branch and switches to it immediately
```

### Option 2: If main branch already exists

```bash
# First, make sure you have a main branch
git checkout -b main

# Then create develop branch
git checkout -b develop
```

## Verify Branch Creation

```bash
# List all branches
git branch

# Output should show:
#   main
# * develop    (asterisk indicates current branch)
```

## Add Files to Git

```bash
# Add all files
git add .

# Verify what will be committed
git status

# Make first commit
git commit -m "Initial commit: Clinic booking system with authentication"
```

## Set Upstream for develop Branch

If you're using a remote repository:

```bash
# Set remote URL (replace with your actual repository)
git remote add origin https://github.com/yourusername/clinic-booking-system.git

# Push develop branch to remote
git push -u origin develop

# Also push main branch
git push -u origin main
```

## Branch Structure for Your Project

```
main (production branch)
├── develop (development branch) ← Current branch
│   ├── feature/authentication ✅ (completed)
│   ├── feature/appointments (upcoming)
│   ├── feature/admin-dashboard (upcoming)
│   └── ...
```

## Recommended Git Workflow

### For Feature Development

```bash
# Start from develop branch
git checkout develop
git pull origin develop

# Create a new feature branch
git checkout -b feature/new-feature-name

# Make changes and commit
git add .
git commit -m "Add new feature"

# Push feature branch
git push -u origin feature/new-feature-name

# Create Pull Request on GitHub/GitLab
# After approval, merge to develop
```

### For Releases

```bash
# Create release branch from develop
git checkout -b release/v1.0.0 develop

# Make release preparations
git commit -m "Bump version to 1.0.0"

# Merge to main
git checkout main
git merge --no-ff release/v1.0.0
git tag -a v1.0.0 -m "Release version 1.0.0"

# Merge back to develop
git checkout develop
git merge --no-ff release/v1.0.0

# Delete release branch
git branch -d release/v1.0.0
```

## Common Git Commands

```bash
# Check current branch
git branch

# Switch to different branch
git checkout branch-name

# View commit history
git log --oneline

# View changes
git diff

# Stage specific file
git add filename

# Unstage file
git reset filename

# Undo last commit (keep changes)
git reset --soft HEAD~1

# Undo last commit (discard changes)
git reset --hard HEAD~1

# View remote repositories
git remote -v

# Fetch updates from remote
git fetch origin

# Pull and merge remote changes
git pull origin develop

# Push to remote
git push origin develop
```

## Quick Start Commands

Run these commands in order to get started:

```bash
cd D:\learn\clinic-booking-system

# 1. Initialize git (if not done already)
git init

# 2. Add all files
git add .

# 3. Create initial commit
git commit -m "Initial commit: Clinic booking system with authentication and login fix"

# 4. Create develop branch
git checkout -b develop

# 5. (Optional) Add remote and push
git remote add origin https://github.com/yourusername/clinic-booking-system.git
git push -u origin main
git push -u origin develop
```

## Branch Naming Conventions

For consistency, use these naming conventions:

```
main              - Production-ready code
develop           - Development branch (integration point)
feature/*         - New features (e.g., feature/user-management)
bugfix/*          - Bug fixes (e.g., bugfix/login-error)
hotfix/*          - Urgent production fixes (e.g., hotfix/security-patch)
release/*         - Release preparation (e.g., release/v1.0.0)
docs/*            - Documentation (e.g., docs/api-guide)
```

## Check Your Current Git Status

```bash
# Shows current branch and uncommitted changes
git status

# Shows detailed log
git log --oneline -10

# Shows all branches
git branch -a
```

---

**Next Steps:**
1. Run the quick start commands above
2. Push to GitHub/GitLab if you have a remote repository
3. Start creating feature branches for new functionality
4. Use Pull Requests for code review before merging to develop

