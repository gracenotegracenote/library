package com.library.services;

import com.library.domains.Medium;
import com.library.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediumService {
    public List<Medium> getAllMedia() {
        return Data.media;
    }

    public Medium getMedium(Integer mediumId) {
        return Data.mediaMap.getOrDefault(mediumId, null);
    }
}
