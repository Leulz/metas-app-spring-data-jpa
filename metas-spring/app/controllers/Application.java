package controllers;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.google.common.collect.Lists;

import models.Meta;
import models.MetaRepository;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * The main set of web services.
 */
@Named
@Singleton
public class Application extends Controller {
	
	private static Form<Meta> metaForm = Form.form(Meta.class);
	
    //private final PersonRepository personRepository;
    private final MetaRepository metaRepository;
    
    @Inject
    public Application(final MetaRepository metaRepository) {
    	this.metaRepository = metaRepository;
    }

//    // We are using constructor injection to receive a repository to support our desire for immutability.
//    @Inject
//    public Application(final PersonRepository personRepository) {
//        this.personRepository = personRepository;
//    }

    public Result index() {
    	List<Meta> metas = Lists.newArrayList(metaRepository.findAll());
    	Collections.sort(metas);

        return ok(views.html.index.render(metas));
    }
    
    public Result newMeta() {
    	Form<Meta> filledForm = metaForm.bindFromRequest();
    	
    	if (filledForm.hasErrors()) {
            List<Meta> result = Lists.newArrayList(metaRepository.findAll());
            Collections.sort(result);
			return badRequest(views.html.index.render(result));
		} else {
            Meta novoMeta = filledForm.get();
            
            metaRepository.save(novoMeta);
			
			Logger.debug("Criando Meta: " + filledForm.data().toString() + " como " + novoMeta.getDescricao() + " ID: "+novoMeta.getId());
            
			return redirect(routes.Application.index());
		}
    }
    
    public Result cumprirMeta() {
    	Form<Meta> filledForm = metaForm.bindFromRequest();
    	
    	if (filledForm.hasErrors()) {
            List<Meta> result = Lists.newArrayList(metaRepository.findAll());
            Collections.sort(result);
			return badRequest(views.html.index.render(result));
		} else {
			Long id = Long.parseLong(filledForm.data().get("id"));
            
			Meta meta = metaRepository.findOne(id);
	    	meta.setCumprida(true);
	    	metaRepository.save(meta);
            
			return redirect(routes.Application.index());
		}
    }
    
    public Result deleteMeta() {
    	Form<Meta> filledForm = metaForm.bindFromRequest();
    	
    	if (filledForm.hasErrors()) {
            List<Meta> result = Lists.newArrayList(metaRepository.findAll());
            Collections.sort(result);
			return badRequest(views.html.index.render(result));
		} else {
			Long id = Long.parseLong(filledForm.data().get("id"));
			
	    	metaRepository.delete(id);
            
			return redirect(routes.Application.index());
		}
    }
}
