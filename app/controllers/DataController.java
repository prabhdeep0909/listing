package controllers;
import dao.DbConnector;
import dto.*;
import models.*;

import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;

import java.util.List;


public class DataController extends Controller {
    private FormFactory formFactory;


    @Inject
    public DataController(FormFactory formFactory) {
        this.formFactory = formFactory;

    }

    public Result datapage(Http.Request request) {
        return ok(views.html.datalist.render());
    }

    public Result dataform() {
        return ok(views.html.form.render());
    }

    public Result dataresult(Http.Request request) {
        List<DataModel> data = DataModel.find.all();
        return ok(Json.toJson(data));

    }

    public Result savedata(Http.Request request) {
        Form<DataDto> dataDto = formFactory.form(DataDto.class).bindFromRequest(request);
        DataDto form = dataDto.get();
        String name = form.getName();
        String email = form.getEmail();
        String phone = form.getPhone();

        List<DataModel> objects = DbConnector.find(DataModel.class).select("name,email,phone").where().eq("name", name).findList();
        if (objects.isEmpty()) {

            DataModel dataModel = new DataModel();
            dataModel.setName(name);
            dataModel.setEmail(email);
            dataModel.setPhone(phone);

            DbConnector.save(dataModel);

        }
        return ok(Json.toJson(objects));
    }

    public Result deletedata(Http.Request request, Integer id) {
        DataModel dataModel = DataModel.find.byId(id);
        DbConnector.delete(dataModel);

        return ok("200");
    }

    public Result update() {
        Form<DataDto> dataDto = formFactory.form(DataDto.class).bindFromRequest() ;
        DataDto form = dataDto.get();
        Integer id = form.getId();
        String name = form.getName();
        String email = form.getEmail();
        String phone = form.getPhone();
        DataModel dataModel = DataModel.find.byId(id);


            dataModel.setId(id);
            dataModel.setName(name);
            dataModel.setEmail(email);
            dataModel.setPhone(phone);

            DbConnector.update(dataModel);
        return ok(Json.toJson(dataModel));


    }

}


























///////////////////////////////////////

//    public Result dataresult(Http.Request request){
//        List<dataModel> data = dataModel.find.all();


//        List<SqlRow> data = DbConnector.createSqlQuery("select name, email, phone from data ").findList();
//        List<dataDto> dataList = new ArrayList<>();
//        for (SqlRow row : data) {
//            dataDto mov = new dataDto();
//            mov.setName(row.getString("name"));
//            mov.setName(row.getString("email"));
//            mov.setName(row.getString("phone"));
//
//            dataList.add(mov);
//        }

//        return ok(Json.toJson(data));
