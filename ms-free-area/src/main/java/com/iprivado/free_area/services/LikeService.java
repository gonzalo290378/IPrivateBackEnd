package com.iprivado.free_area.services;

import com.iprivado.free_area.dto.FreeAreaDTO;
import com.iprivado.free_area.dto.LikeResponseDTO;
import com.iprivado.free_area.dto.PrincipalPhotoDTO;
import com.iprivado.free_area.dto.PublicContentDTO;
import com.iprivado.free_area.models.entity.FreeArea;
import com.iprivado.free_area.models.entity.PrincipalPhoto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface LikeService {

    LikeResponseDTO toggle(Long contentId, String username);

    LikeResponseDTO getStatus(Long contentId, String username);


}