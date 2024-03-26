package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.advert_params.AddAdvertParams;
import com.laughbro.welcome.vo.params.advert_params.DeleteAdvertParams;
import com.laughbro.welcome.vo.params.advert_params.EditAdvertParams;

public interface AdvertService {
    Result AddAdvert(AddAdvertParams params);

    Result DeleteAdvert(DeleteAdvertParams params);

    Result EditAdvert(EditAdvertParams params);

    Result GetAdvertByKeyword(String keyword);

    Result GetAdvertAll();
}
