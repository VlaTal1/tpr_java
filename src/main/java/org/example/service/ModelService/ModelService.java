package org.example.service.ModelService;

import org.example.bom.Model;
import org.example.exception.NotFoundException;

public interface ModelService {

    Model save(Model model);

    Model findById(Long id) throws NotFoundException;
}
