package com.compoldata.induflow.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.compoldata.induflow.DTO.request.OccurrenceForm;
import com.compoldata.induflow.exceptions.OccurrenceNotFoundException;
import com.compoldata.induflow.model.enums.AffectedEquipment;
import com.compoldata.induflow.model.enums.OccurrenceType;
import com.compoldata.induflow.model.enums.StopCodes;
import com.compoldata.induflow.service.OcurrenceService;
import com.compoldata.induflow.service.ProductionOrderService;

@Controller
public class OcurrenceController {

    @Autowired
    private OcurrenceService ocurrenceService;
    @Autowired
    private ProductionOrderService productionOrderService;

    // retorna ocorrencia pelo id
    @GetMapping("/ocurrencedetails/{ocurrenceId}")
    public ModelAndView ocurrenceDetails(@PathVariable Long ocurrenceId) {
        var model = ocurrenceService.ocurrenceDetails(ocurrenceId);

        ModelAndView mView = new ModelAndView("ocurrence-details");

        return mView.addObject("ocurrence", model);

    }

    // retorna todas as ocorrências
    @GetMapping("/findallOcurrences")
    public ModelAndView findAllOcurrences() {
        ModelAndView mView = new ModelAndView("list-all-ocurrences");
        mView.addObject("ocurrences", ocurrenceService.findAllOcurrences());
        return mView;
    }

    // retorna a pagina de cadastro de Setup
    @GetMapping("/startSetupOccurrence/{productionOrderId}")
    public ModelAndView startSetupOccurrence(@PathVariable Long productionOrderId) {
        var model = Map.of(
                "ocurrence",
                new OccurrenceForm(null, null, null, null, null, null, null, productionOrderId, null),

                "productionOrder", productionOrderService.productionOrderInformation(productionOrderId));

        return new ModelAndView("setup-register", model);
    }

    // retorna a pagina de cadastro de ocorrencia
    @GetMapping("/registerOcurrence/{productionOrderId}")
    public ModelAndView registerNewOcurrence(@PathVariable Long productionOrderId) {
        var model = Map.of(
                "ocurrence",
                new OccurrenceForm(null, null, null, null, null, null, null, productionOrderId, null),
                "stopcodes", StopCodes.values(),
                "affectedEquipment", AffectedEquipment.values(),

                "ocorrenceType", OccurrenceType.values(),
                "productionOrder", productionOrderService.productionOrderInformation(productionOrderId));

        return new ModelAndView("ocorrency-register", model);
    }

    // registra uma nova ocorrencia
    @PostMapping("/registerOcurrence")
    public String registerNewOcurrence(@ModelAttribute OccurrenceForm ocurrenceForm) {

        if (ocurrenceForm.ProductionOrderId() == null) {
            throw new IllegalArgumentException("id da ordem de produção está nulo");
        }

        ocurrenceService.registerOcurrence(ocurrenceForm);
        // Redireciona para o dashboard com o ID da ordem de produção
        return "redirect:/dashboard/" + ocurrenceForm.ProductionOrderId();
    }

    // registra SETUP
    @PostMapping("/startSetupOccurrence")
    public String startSetupOccurrence(@ModelAttribute OccurrenceForm occurrenceForm) {

        if (occurrenceForm.ProductionOrderId() == null) {
            throw new IllegalArgumentException("id da ordem de produção está nulo");
        }

        ocurrenceService.startSetupOccurrence(occurrenceForm);
        // Redireciona para o dashboard com o ID da ordem de produção
        return "redirect:/dashboard/" + occurrenceForm.ProductionOrderId();
    }

    // finaliza a o correncia
    @PostMapping("/endocurrence/{ocurrenceId}")
    public String endOcurrence(@PathVariable Long ocurrenceId) {
        ocurrenceService.endOcurrence(ocurrenceId);
        return "redirect:/findallOcurrences";

    }

    // detalhes da ocorrencia
    @GetMapping("/setupdetails/{ocurrenceId}")
    public ModelAndView setupDetails(@PathVariable Long ocurrenceId) {
        var model = ocurrenceService.ocurrenceDetails(ocurrenceId);
        ModelAndView mView = new ModelAndView("setup");
        if (model.ocorrenceType() != OccurrenceType.PREPARAÇÃO) {
            throw new OccurrenceNotFoundException(
                    "Ocorreu um erro ao tentar carregar os dados nesta página. Para resolver a questão rapidamente, por favor, entre em contato com o suporte técnico.");
        }
        return mView.addObject("setup", model);

    }

}
