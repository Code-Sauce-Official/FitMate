### Test1 : New User Verification  
**_Screenshot/gif_**<br>
<img src="TestSignUpAndSignIn_Snapshots/New_user_verification.jpg" width="300"/><br>
**_Expected Result_**:  It denied my login as I was a new User<br>
**_Actual Result_**: Positive<br>

### Test2 : Verifying already registered user  
**_Screenshot/gif_**<br>  
<img src="TestSignUpAndSignIn_Snapshots/Already_registered_user.jpg" width="300"/><br>
**_Expected Result_**:  It gave me a message that this email is already registered<br>
**_Actual Result_**:  Positive<br>
     
### Test3 : SignUp 
**_Screenshot/gif_**<br>  
<img src="TestSignUpAndSignIn_Snapshots/sign_Up_snapshot.jpg" width="300"/><br>
**_Expected Result_**:  SignUp successful<br>
**_Actual Result_**:  Positive<br>

### Test4 : SignIn  
**_Screenshot/gif_**<br>  
<img src="TestSignUpAndSignIn_Snapshots/sign_in_snapshot.jpg" width="300"/><br>
**_Expected Result_**:  SignIn successful<br>
**_Actual Result_**:  Positive<br>

     
### Test5 : Empty Fields
**_Screenshot/gif_**<br>  
<img src="TestSignUpAndSignIn_Snapshots/Empty_Fields_FitMate.jpg" width="300"/><br>
**_Expected Result_**: Email and Password cannot be empty<br>
**_Actual Result_**:  Positive<br>
     
### Test6 : Password Constraints in sign Up 
**_Screenshot/gif_**<br>  
<img src="TestSignUpAndSignIn_Snapshots/Password_Conatraint1_FitMate.jpg" width="300"/><br>
     <br>
    <img src="TestSignUpAndSignIn_Snapshots/Password_Constraint2_FitMate.jpg" width="300"/><br>
**_Expected Result_**:  Password must contain atleast one special character and minimum 8 digits<br>
**_Actual Result_**:  Positive<br>
     

### Test7 : Resend Verification Email  
**_Screenshot/gif_**<br>  
<img src="TestSignUpAndSignIn_Snapshots/Resend_Verification_Email_FitMate.jpg" width="300"/><br>
**_Expected Result_**: An verification link has been sent to your email address<br>
**_Actual Result_**:  Positive<br>

### Test8 : Forgot Password
**_Screenshot/gif_**<br>  
<img src="TestSignUpAndSignIn_Snapshots/Forgot_Password_FitMate.jpg" width="300"/><br>
**_Expected Result_**: Instructions for resetting password has been sent to your email address<br>
**_Actual Result_**:  Positive<br>

### Test9 : Failed Login Attempts
**_Screenshot/gif_**<br>   
<img src="TestSignUpAndSignIn_Snapshots/Failed_Login_Attempts_FitMate.jpg" width="300"/><br>
**_Expected Result_**: I tried sign in with incorrect credentials for 3 times only but it says we have blocked all requests from this device due to many failed login attempts .<br>
**_Actual Result_**:  Negative (In case someone not intentionally try to sign in with wrong credentials then in that case this is an issue)<br>

### Test10 : Badly Formatted Email
**_Screenshot/gif_**<br>  
<img src="TestSignUpAndSignIn_Snapshots/Email_Badly_Formatted_FitMate.jpg" width="300"/><br>
**_Expected Result_**: Email should have been accepted<br>
**_Actual Result_**:  Negative (Someone can have this type of email id )<br> 

### Test11 : Badly Formatted Email2
**_Screenshot/gif_**<br>  
<img src="TestSignUpAndSignIn_Snapshots/Number_As_Email_FitMate.jpg" width="300"/><br>
**_Expected Result_**: When I try to use phone number in case of email it says badly formatted email , number should also be a way to sign in <br>
**_Actual Result_**:  Negative<br>     

### Test12 : Passwards Do Not Match
**_Screenshot/gif_**<br>  
<img src="TestSignUpAndSignIn_Snapshots/Password_Dont_Match_FitMate.jpg" width="300"/><br>
**_Expected Result_**: When I tried sign Up with different entries in 'password' and 'Confirm Password' it says passwords does not match<br>
**_Actual Result_**:  Positive<br>     

### Test13 : Email Already in Use 
**_Screenshot/gif_**<br>  
<img src="TestSignUpAndSignIn_Snapshots/Email_AlreadyIN_Use_FitMate.jpg" width="300"/><br>
**_Expected Result_**: When I tried sign Up with already registered email it says email already in use<br>
**_Actual Result_**:  Positive<br>   

### Test14 : Password must contain Atleast 1 Letter  
**_Screenshot/gif_**<br>  
<img src="TestSignUpAndSignIn_Snapshots/Password_Atleast_oneLetter_FitMate.jpg" width="300"/><br>
**_Expected Result_**: Password must contain atleast one letter<br>
**_Actual Result_**:  Positive<br>     

### Test15 : Password must contain Atleast 1 Digit  
**_Screenshot/gif_**<br>   
<img src="TestSignUpAndSignIn_Snapshots/Password_Atleast_1Digit_FitMate.jpg" width="300"/><br>
**_Expected Result_**: Password must contain atleast one digit<br>
**_Actual Result_**:  Positive<br>     
