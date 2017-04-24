package com.yihaobeta.healthguard.api;

import com.yihaobeta.healthguard.beans.CookBookDetail;
import com.yihaobeta.healthguard.beans.CookBookList;
import com.yihaobeta.healthguard.beans.KnowledgeDetail;
import com.yihaobeta.healthguard.beans.KnowledgeList;
import com.yihaobeta.healthguard.beans.MedicineDetail;
import com.yihaobeta.healthguard.beans.MedicineList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yihaobeta on 2017/3/10.
 * 网络请求API类
 * 返回RxJava的Observable
 */

public interface ApiService {

    @GET("lore/list")
    Observable<KnowledgeList> getKnowledgeList(@Query("id") int id, @Query("page") int page, @Query("rows") int rows);

    @GET("lore/news")
    Observable<KnowledgeList> getKnowledgeNews(@Query("id") int lastId, @Query("rows") int rows);

    @GET("lore/show")
    Observable<KnowledgeDetail> getKnowledgeDetail(@Query("id") int id);

    @GET("drug/list")
    Observable<MedicineList> getMedicineList(@Query("id") int id, @Query("page") int page, @Query("rows") int rows);

    @GET("drug/show")
    Observable<MedicineDetail> getMedicineDetailByID(@Query("id") int id);

    @GET("drug/code")
    Observable<MedicineDetail> getMedicineDetailByCode(@Query("code") String code);

    @GET("drug/name")
    Observable<MedicineDetail> getMedicineDetailByName(@Query("name") String name);

    @GET("cook/list")
    Observable<CookBookList> getCookListById(@Query("id") int id, @Query("page") int page, @Query("rows") int rows);

    @GET("cook/show")
    Observable<CookBookDetail> getCookBookDetail(@Query("id") int id);
}
