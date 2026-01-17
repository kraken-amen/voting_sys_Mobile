# Voting System (voting_sys)

An Android mobile application for managing electronic voting with real-time results tracking. This system allows voters to authenticate, view candidates, cast votes, and view election results in real-time.

## 📱 Features

### For Voters (Électeurs)
- **CIN Authentication**: Secure login using 8-digit National ID (CIN)
- **Vote Prevention**: Automatic detection to prevent duplicate voting
- **Candidate Browsing**: View all registered candidates with their details
- **Secure Voting**: Cast votes for preferred candidates
- **Real-time Updates**: Live candidate list updates

### For Candidates
- **Self-Registration**: Candidates can register themselves
- **Profile Management**: Add name, presentation, and program details
- **Vote Tracking**: Automatic vote count tracking

### General Features
- **Results Dashboard**: Real-time voting results with vote counts and percentages
- **Firebase Integration**: Cloud-based data storage and synchronization
- **User-Friendly Interface**: Intuitive navigation with role-based entry points

## 🏗️ Architecture

### Technology Stack
- **Language**: Java
- **Platform**: Android (API 24+, Target API 36)
- **Backend**: Firebase Realtime Database
- **UI Components**: RecyclerView, Material Design
- **Build System**: Gradle

### Project Structure
```
voting_sys2/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/voting_sys/
│   │       │   ├── EntryActivity.java          # Main entry point
│   │       │   ├── ElecteurLoginActivity.java  # Voter authentication
│   │       │   ├── MainActivity.java           # Candidate list for voters
│   │       │   ├── DetailsActivity.java        # Candidate details & voting
│   │       │   ├── AddCandidateActivity.java   # Candidate registration
│   │       │   ├── ResultsActivity.java        # Election results
│   │       │   ├── Candidate.java              # Candidate model
│   │       │   ├── CandidateAdapter.java       # RecyclerView adapter
│   │       │   └── ResultsAdapter.java         # Results adapter
│   │       ├── res/
│   │       │   ├── layout/                     # UI Interfaces
│   │       │   │   ├── activity_entry.xml              # Entry screen interface
│   │       │   │   ├── activity_electeur_login.xml     # Voter login interface
│   │       │   │   ├── activity_main_voter.xml         # Candidate list interface
│   │       │   │   ├── activity_details.xml            # Candidate details interface
│   │       │   │   ├── activity_add_candidate.xml      # Add candidate interface
│   │       │   │   ├── activity_results.xml            # Results dashboard interface
│   │       │   │   ├── item_candidate.xml              # Candidate list item layout
│   │       │   │   └── item_result.xml                 # Result list item layout
│   │       │   ├── drawable/                   # Images and graphics
│   │       │   ├── values/                     # Strings, colors, styles
│   │       │   └── mipmap-*/                   # App icons
│   │       └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
└── settings.gradle
```

## 🚀 Getting Started

### Prerequisites
- **Android Studio**: Arctic Fox or later
- **JDK**: Java 11 or higher
- **Android SDK**: API Level 24 (Android 7.0) minimum
- **Firebase Account**: For Realtime Database setup

### Firebase Setup

1. **Create a Firebase Project**
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Create a new project or use an existing one

2. **Add Android App to Firebase**
   - Register your app with package name: `com.example.voting_sys`
   - Download `google-services.json`
   - Place it in `app/` directory

3. **Enable Realtime Database**
   - In Firebase Console, navigate to Realtime Database
   - Create a database in test mode (or configure security rules)
   - Note your database URL

4. **Database Structure**
   ```json
   {
     "Candidate": {
       "candidateId1": {
         "id": "candidateId1",
         "name": "Candidate Name",
         "presentation": "Brief introduction",
         "program": "Electoral program",
         "votesCount": 0
       }
     },
     "Votes": {
       "12345678": "candidateId1"
     }
   }
   ```

### Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/kraken-amen/voting_sys_Mobile
   cd voting_sys2
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the project directory

3. **Configure Firebase**
   - Ensure `google-services.json` is in the `app/` folder
   - Sync Gradle files

4. **Build the Project**
   - Click "Build" → "Make Project" or press `Ctrl+F9`
   - Wait for Gradle sync and build to complete

5. **Run the Application**
   - Connect an Android device or start an emulator
   - Click "Run" → "Run 'app'" or press `Shift+F10`

## 📖 Usage Guide

### Application Flow

1. **Entry Screen**
   - Choose your role:
     - **Électeur** (Voter): To cast a vote
     - **Candidat** (Candidate): To register as a candidate
     - **View Results**: To see current voting results

2. **Voter Flow**
   ```
   Entry → Login (CIN) → Candidate List → Candidate Details → Vote Confirmation
   ```
   - Enter your 8-digit CIN
   - System checks if you've already voted
   - Browse candidates and view their programs
   - Select a candidate to vote
   - Vote is recorded in Firebase

3. **Candidate Flow**
   ```
   Entry → Add Candidate → Fill Form → Submit
   ```
   - Enter candidate name
   - Provide presentation/introduction
   - Describe electoral program
   - Submit to register

4. **Results Flow**
   ```
   Entry → View Results → Real-time Dashboard
   ```
   - See all candidates with vote counts
   - View vote percentages
   - Results update in real-time

## 🔧 Configuration

### Build Configuration
- **Application ID**: `com.example.voting_sys`
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **Compile SDK**: 36
- **Version**: 1.0

### Dependencies
- AndroidX AppCompat
- Material Design Components
- ConstraintLayout
- RecyclerView
- Firebase Realtime Database
- Firebase Firestore
- Firebase Authentication

## 🔒 Security Considerations

> [!WARNING]
> This application is designed for educational purposes. For production use, implement the following security measures:

- **Firebase Security Rules**: Configure proper read/write rules
- **CIN Validation**: Add server-side validation
- **Vote Encryption**: Encrypt vote data
- **Authentication**: Implement proper user authentication (currently uses CIN only)
- **Rate Limiting**: Prevent spam and abuse
- **Data Backup**: Regular database backups

### Recommended Firebase Rules
```json
{
  "rules": {
    "Candidate": {
      ".read": true,
      ".write": "auth != null"
    },
    "Votes": {
      "$cin": {
        ".read": "auth != null",
        ".write": "!data.exists() && auth != null"
      }
    }
  }
}
```

## 🧪 Testing

### Manual Testing Checklist
- [ ] Voter can login with valid 8-digit CIN
- [ ] System prevents duplicate voting
- [ ] Candidate list displays correctly
- [ ] Vote is recorded in Firebase
- [ ] Candidate registration works
- [ ] Results display accurate vote counts
- [ ] Navigation between screens works
- [ ] Back to home functionality works

### Test Scenarios

**Scenario 1: First-time Voter**
1. Launch app → Select "Électeur"
2. Enter CIN: `12345678`
3. View candidates and select one
4. Confirm vote is recorded

**Scenario 2: Duplicate Vote Prevention**
1. Try to vote again with same CIN
2. Verify error message appears

**Scenario 3: Candidate Registration**
1. Select "Candidat"
2. Fill all fields
3. Verify candidate appears in database

## 🐛 Troubleshooting

### Common Issues

**Firebase Connection Failed**
- Verify `google-services.json` is in the correct location
- Check internet connectivity
- Ensure Firebase project is properly configured

**Build Errors**
- Clean and rebuild: `Build → Clean Project`, then `Build → Rebuild Project`
- Invalidate caches: `File → Invalidate Caches / Restart`
- Check Gradle sync status

**App Crashes on Vote**
- Check Firebase Database rules
- Verify database structure matches expected format
- Check Logcat for error messages

## 📝 Future Enhancements

- [ ] Admin dashboard for election management
- [ ] Email/SMS verification for voters
- [ ] Candidate photo uploads
- [ ] Multi-language support
- [ ] Vote analytics and statistics
- [ ] Export results to PDF
- [ ] Offline voting with sync
- [ ] Biometric authentication

## 👥 Contributing

Contributions are welcome! Please follow these steps:
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


## 📧 Contact

For questions or support, please contact kraken.amen@gmail.com

---

**Note**: This application uses Firebase Realtime Database. Ensure you have proper Firebase configuration before running the app.
