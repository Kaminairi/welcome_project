package com.laughbro.welcome.service;

import com.laughbro.welcome.vo.Result;
import com.laughbro.welcome.vo.params.bag_params.BagViewItemParams;

public interface BagService {

    Result bag_viewall(BagViewItemParams bagViewItemParams);

}
