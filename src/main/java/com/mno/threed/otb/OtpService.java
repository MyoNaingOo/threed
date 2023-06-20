package com.mno.threed.otb;

import com.mno.threed.config.JwtService;
import com.mno.threed.entity.Token;
import com.mno.threed.entity.User;
import com.mno.threed.reposity.TokenRepo;
import com.mno.threed.service.Email;
import com.mno.threed.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepo otpRepo;

    private final UserService userService;
    private final TokenRepo tokenRepo;
    private final JwtService jwtService;
    private final Email emailser;

    private Otp findByOtp(int otp){
        return otpRepo.findByOtp(otp).orElse(null);
    }

    public String sendOtp(String email,String token){
        Random random = new Random();
        int otpCode = random.nextInt(100000,999999);
        System.out.println(otpCode);
//        emailser.sendmail(email,"Login OTP","your otp is:"+otpCode );
        Otp createOtp = Otp.builder()
                .email(email)
                .token(token)
                .otp(otpCode)
                .build();
        otpRepo.save(createOtp);

        return email;
    }

    public OtpDtoResponse authenticateByotp(OtpDtoRequest otpDtoRequest){
        Otp otp= findByOtp(otpDtoRequest.getOtp());
        System.out.println(otp.getEmail());
        System.out.println(otpDtoRequest.getEmail());
        otpRepo.delete(otp);
        if (otpDtoRequest.getEmail().equals(otp.getEmail())){
            String email = otp.getEmail();
            User user = userService.userfindByEmail(email);

            OtpDtoResponse otpDtoResponse;
            if (user == null){
                otpDtoResponse = OtpDtoResponse.builder()
                        .email(email)
                        .checkotp(true)
                        .logined(false)
                        .massage("User is not register")
                        .build();
            }else {
                otpDtoResponse = OtpDtoResponse.builder()
                        .email(email)
                        .user(user)
                        .token(otp.getToken())
                        .checkotp(true)
                        .logined(true)
                        .massage("Successful Login")
                        .build();

            }
            return otpDtoResponse;
        }else {
            return OtpDtoResponse.builder()
                    .email(otpDtoRequest.getEmail())
                    .checkotp(false)
                    .logined(false)
                    .massage("Not same Otp Try again")
                    .build();
        }
    }

    public OtpDtoResponse registerByotp(OtpDtoRequest otpDtoRequest) {
        Otp otp= findByOtp(otpDtoRequest.getOtp());
        System.out.println(otp.getEmail());
        System.out.println(otpDtoRequest.getEmail());
        otpRepo.delete(otp);
        OtpDtoResponse otpDtoResponse;
        if (otpDtoRequest.getEmail().equals(otp.getEmail())){
            String email = otp.getEmail();
            User user = userService.userfindByEmail(email);


                otpDtoResponse = OtpDtoResponse.builder()
                        .email(email)
                        .user(user)
                        .token(otp.getToken())
                        .checkotp(true)
                        .logined(true)
                        .massage("Successful Login")
                        .build();
            return otpDtoResponse;
        }else {
            return OtpDtoResponse.builder()
                    .email(otpDtoRequest.getEmail())
                    .checkotp(false)
                    .logined(false)
                    .massage("email or otp is fail")
                    .build();
        }
    }
}
