package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.bag_params.Bag_ViewItem_Params;

public interface BagService {

    Result bag_viewall(Bag_ViewItem_Params bagViewItemParams);

}
