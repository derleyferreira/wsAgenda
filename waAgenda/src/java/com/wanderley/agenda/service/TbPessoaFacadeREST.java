/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wanderley.agenda.service;

import com.google.gson.Gson;
import com.wanderley.agenda.Login;
import com.wanderley.agenda.TbPessoa;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

        
/**
 *
 * @author Wanderley
 */
@Stateless
@Path("com.wanderley.agenda.tbpessoa")
public class TbPessoaFacadeREST extends AbstractFacade<TbPessoa> {

    @PersistenceContext(unitName = "waAgendaPU")
    private EntityManager em;

    public TbPessoaFacadeREST() {
        super(TbPessoa.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(TbPessoa entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, TbPessoa entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public TbPessoa find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TbPessoa> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TbPessoa> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("{login}/{senha}")
    @Produces({MediaType.TEXT_PLAIN})
    public String validaLogin(@PathParam("login") String login, @PathParam("senha") String senha){
       Query q = em.createQuery("SELECT t FROM TbPessoa t WHERE t.pesEmail = :pesEmail AND t.pesSenha = :pesSenha");
       q.setParameter("pesEmail", login);
       q.setParameter("pesSenha", senha);
       
       if (!q.getResultList().isEmpty()){
           return "ok";    
       }
       else
       {
          return "falso";
       }
              
    }    
    
    @POST  
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String validarLogin(String loginJson){
        
        Gson gson = new Gson();
        
        Login login = gson.fromJson(loginJson, Login.class);                
        
        Query q = em.createQuery("SELECT t FROM TbPessoa t WHERE t.pesEmail = :pesEmail and t.pesSenha = :pesSenha");
        q.setParameter("pesEmail", login.getLogin().toString());
        q.setParameter("pesSenha", login.getSenha().toString());
        
        if(!q.getResultList().isEmpty())
           return gson.toJson(q.getResultList().get(0));
        else
           return "";
    }    
        
    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
