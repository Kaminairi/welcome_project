package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.me_params.MeChangeImgParams;
import com.laughbro.welcome.vo.params.me_params.MeChangeNameParams;
import com.laughbro.welcome.vo.params.me_params.MeChangePwdParams;

public interface MeService {
    Result CheckInformation(String userid);

    Result ChangeName(MeChangeNameParams params);

    Result ChangeImg(MeChangeImgParams params);

    Result ChangePwd(MeChangePwdParams params);
}
