package com.atyume.ibabym.Dao;
import android.content.Context;
import android.util.Log;

import com.atyume.ibabym.basics.Inoculation;
import com.atyume.greendao.gen.InoculationDao;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class DaoUtils {
    private static final String TAG = DaoUtils.class.getSimpleName();
    private DaoManager mManager;

    public DaoUtils(Context context){
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成baby记录的插入，如果表未创建，先创建Inoculation表
     * @param baby
     * @return
     */
    public boolean insertInoculation(Inoculation baby){
        boolean flag = false;
        flag = mManager.getDaoSession().getInoculationDao().insert(baby) == -1 ? false : true;
        Log.i(TAG, "insert Meizi :" + flag + "-->" + baby.toString());
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     * @param babyList
     * @return
     */
    public boolean insertMultMeizi(final List<Inoculation> babyList) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (Inoculation baby : babyList) {
                        mManager.getDaoSession().insertOrReplace(baby);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改一条数据
     * @param baby
     * @return
     */
    public boolean updateInoculation(Inoculation baby){
        boolean flag = false;
        try {
            mManager.getDaoSession().update(baby);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单条记录
     * @param baby
     * @return
     */
    public boolean deleteInoculation(Inoculation baby){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(baby);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     * @return
     */
    public boolean deleteAll(){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(Inoculation.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<Inoculation> queryAllInoculation(){
        return mManager.getDaoSession().loadAll(Inoculation.class);
    }

    /**
     * 根据主键id查询记录
     * @param key
     * @return
     */
    public Inoculation queryMeiziById(long key){
        return mManager.getDaoSession().load(Inoculation.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<Inoculation> queryMeiziByNativeSql(String sql, String[] conditions){
        return mManager.getDaoSession().queryRaw(Inoculation.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     * @return
     */
    public List<Inoculation> queryMeiziByQueryBuilder(long id){
        QueryBuilder<Inoculation> queryBuilder = mManager.getDaoSession().queryBuilder(Inoculation.class);
        return queryBuilder.where(InoculationDao.Properties.Id.eq(id)).list();
//        return queryBuilder.where(MeiziDao.Properties._id.ge(id)).list();
    }
}
