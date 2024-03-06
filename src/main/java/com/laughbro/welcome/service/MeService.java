package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.me_params.MeChangeImgParams;
import com.laughbro.welcome.vo.params.me_params.MeChangeNameParams;
import com.laughbro.welcome.vo.params.me_params.MeChangePwdParams;
import com.laughbro.welcome.vo.params.me_params.MeCheckInformationParams;

public interface MeService {
    Result CheckInformation(MeCheckInformationParams params);

    Result ChangeName(MeChangeNameParams params);

    Result ChangeImg(MeChangeImgParams params);

    Result ChangePwd(MeChangePwdParams params);
}
