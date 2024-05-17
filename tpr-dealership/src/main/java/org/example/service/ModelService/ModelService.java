package org.example.service.ModelService;

import org.example.bom.Model;

public interface ModelService {

    Model save(Model model);

    Model findById(Long id);
}
