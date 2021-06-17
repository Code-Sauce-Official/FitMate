* _Test1 New User Verification_  
     _Screenshot/gif_:  
     <img src="TestSignUpAndSignIn_Snapshots/New_user_verification.jpg" width="300"/><br>
     _Expected Result_:  It denied my login as I was a new User<br>
     _Actual Result_: Positive<br>

* _Test2 Verifying already registered user_  
     _Screenshot/gif_:  
     <img src="TestSignUpAndSignIn_Snapshots/Already_registered_user.jpg" width="300"/><br>
     _Expected Result_:  It gave me a message that this email is already registered<br>
     _Actual Result_:  Positive<br>
     
* _Test3 SignUp_  
     _Screenshot/gif_:  
     <img src="TestSignUpAndSignIn_Snapshots/sign_Up_snapshot.jpg" width="300"/><br>
     _Expected Result_:  SignUp successful<br>
     _Actual Result_:  Positive<br>

* _Test4 SignIn_  
     _Screenshot/gif_:  
     <img src="TestSignUpAndSignIn_Snapshots/sign_in_snapshot.jpg" width="300"/><br>
     _Expected Result_:  SignIn successful<br>
     _Actual Result_:  Positive

     
* _Test5 Empty Fields_  
     _Screenshot/gif_:  
     <img src="TestSignUpAndSignIn_Snapshots/Empty_Fields_Fitmate.jpg" width="300"/><br>
     _Expected Result_: Email and Password cannot be empty<br>
     _Actual Result_:  Positive
     
* _Test6 Password Constraints in sign Up_  
     _Screenshot/gif_:  
     <img src="TestSignUpAndSignIn_Snapshots/Password_Conatraint1_FitMate.jpg" width="300"/><br>
     <br>
    <img src="TestSignUpAndSignIn_Snapshots/Password_Constraint2_FitMate.jpg" width="300"/><br>
     _Expected Result_:  Password must contain atleast one special character and minimum 8 digits<br>
     _Actual Result_:  Positive
     

 * _Test7 Resend Verification Email_  
     _Screenshot/gif_:  
     <img src="TestSignUpAndSignIn_Snapshots/Resend_Verification_Email_FitMate.jpg" width="300"/><br>
     _Expected Result_: An verification link has been sent to your email address<br>
     _Actual Result_:  Positive

 * _Test8 Forgot Password_  
     _Screenshot/gif_:  
     <img src="TestSignUpAndSignIn_Snapshots/Forgot_Password_FitMate.jpg" width="300"/><br>
     _Expected Result_: Instructions for resetting password has been sent to your email address<br>
     _Actual Result_:  Positive

 * _Test9 Failed Login Attempts_  
     _Screenshot/gif_:  
     <img src="TestSignUpAndSignIn_Snapshots/Failed_Login_Attempts_FitMate.jpg" width="300"/><br>
     _Expected Result_: I tried sign in with incorrect credentials for 3 times only but it says we have blocked all requests from this device due to many failed login attempts .<br>
     _Actual Result_:  Negative (In case someone not intentionally try to sign in with wrong credentials then in that case this is an issue)

* _Test10 Badly Formatted Email_  
     _Screenshot/gif_:  
     <img src="TestSignUpAndSignIn_Snapshots/Email_Badly_Formatted_FitMate.jpg" width="300"/><br>
     _Expected Result_: Email should have been accepted<br>
     _Actual Result_:  Negative (Someone can have this type of email id ) 

* _Test11 Badly Formatted Email2_  
     _Screenshot/gif_:  
     <img src="TestSignUpAndSignIn_Snapshots/Number_As_Email_FitMate.jpg" width="300"/><br>
     _Expected Result_: When I try to use phone number in case of email it says badly formatted email , number should also be a way to sign in <br>
     _Actual Result_:  Negative     

* _Test12 Passwards Do Not Match_  
     _Screenshot/gif_:  
     <img src="TestSignUpAndSignIn_Snapshots/Password_Dont_Match_FitMate.jpg" width="300"/><br>
     _Expected Result_: When I tried sign Up with different entries in 'password' and 'Confirm Password' it says passwords does not match<br>
     _Actual Result_:  Positive     

* _Test13 Email Already in Use_  
     _Screenshot/gif_:  
     <img src="TestSignUpAndSignIn_Snapshots/Email_AlreadyIN_Use_FitMate.jpg" width="300"/><br>
     _Expected Result_: When I tried sign Up with already registered email it says email already in use<br>
     _Actual Result_:  Positive   

* _Test14 Password must contain Atleast 1 Letter_  
     _Screenshot/gif_:  
     <img src="TestSignUpAndSignIn_Snapshots/Password_Atleast_oneLetter_FitMate.jpg" width="300"/><br>
     _Expected Result_: Password must contain atleast one letter<br>
     _Actual Result_:  Positive     

* _Test15 Password must contain Atleast 1 Digit_  
     _Screenshot/gif_:  
     <img src="TestSignUpAndSignIn_Snapshots/Password_Atleast_1Digit_FitMate.jpg" width="300"/><br>
     _Expected Result_: Password must contain atleast one digit<br>
     _Actual Result_:  Positive     