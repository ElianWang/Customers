package com.kd.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.kd.dao.BaseDao;
import com.kd.util.HibernateHandler;
import com.kd.util.ObjectUtil;
import com.kd.util.Pagination;

@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {

    protected Class<T> entityClazz;

    protected SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public BaseDaoImpl() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            this.entityClazz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            this.entityClazz = null;
        }
    }

    @Resource
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
    public void clearSession() {
    	getSession().clear();
    }

    @SuppressWarnings("unchecked")
    public Object save(Object entity) {
        return (T) getSession().save(entity);
    }

    public void delete(Object entity) {
        getSession().delete(entity);
    }

    public void update(Object entity) {
        getSession().update(entity);
    }

    public void saveOrUpdate(Object entity) {
        getSession().saveOrUpdate(entity);
    }

    public void saveAll(Collection<?> entities) {
        for (@SuppressWarnings("rawtypes")
        Iterator localIterator = entities.iterator(); localIterator.hasNext();) {
            Object entity = localIterator.next();
            getSession().save(entity);
        }
    }

    public void deleteAll(Collection<?> entities) {
        for (@SuppressWarnings("rawtypes")
        Iterator localIterator = entities.iterator(); localIterator.hasNext();) {
            Object entity = localIterator.next();
            getSession().delete(entity);
        }
    }

    public void updateAll(Collection<?> entities) {
        for (@SuppressWarnings("rawtypes")
        Iterator localIterator = entities.iterator(); localIterator.hasNext();) {
            Object entity = localIterator.next();
            getSession().update(entity);
        }
    }

    public void saveOrUpdateAll(Collection<?> entities) {
        for (@SuppressWarnings("rawtypes")
        Iterator localIterator = entities.iterator(); localIterator.hasNext();) {
            Object entity = localIterator.next();
            getSession().saveOrUpdate(entity);
        }
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    public <T> T get(Class<T> entityClass, Serializable id) {
        return (T) getSession().get(entityClass, id);
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "hiding" })
    public <T> T get(CharSequence queryString, Object... params) {
        Query qry = getSession().createQuery(queryString.toString());
        for (int i = 0; i < params.length; ++i) {
            qry.setParameter(i, params[i]);
        }
        List list = qry.setMaxResults(1).list();
        if (list.isEmpty())
            return null;
        return (T) list.get(0);
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    public <T> T get(CharSequence queryString, Map<String, Object> params) {

        Query qry = getSession().createQuery(queryString.toString());
        setParameter(qry, params);
        @SuppressWarnings("rawtypes")
        List list = qry.setMaxResults(1).list();
        if (list.isEmpty())
            return null;
        return (T) list.get(0);
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    public <T> List<T> findList(CharSequence queryString, Object... params) {
        Query query = getSession().createQuery(queryString.toString());
        if(params != null){
        	for (int i = 0; i < params.length; ++i) {
                query.setParameter(i, params[i]);
            }
        }
        return query.list();
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    public <T> List<T> findListByMap(CharSequence queryString, Map<String, Object> params) {
        Query query = getSession().createQuery(queryString.toString());
        if (params != null && !params.isEmpty()) {
        	setParameter(query, params);
		}
        return query.list();
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    public <T> Pagination<T> findPagination(CharSequence queryString, int pageIndex, int pageSize, Object... params) {
        Query query = getSession().createQuery(queryString.toString());

        if ((pageSize > 0) && (pageIndex > 0)) {
            query.setFirstResult((pageIndex < 2) ? 0 : (pageIndex - 1) * pageSize);
            query.setMaxResults(pageSize);
        }

        for (int i = 0; i < params.length; ++i) {
            query.setParameter(i, params[i]);
            System.out.println(params[i]);
        }
        @SuppressWarnings("rawtypes")
        List items = query.list();
        long rowsCount = 0L;

        if ((pageSize > 0) && (pageIndex > 0)) {
            String hql = parseSelectCount(queryString.toString());
            rowsCount = ((Long) get(hql, params)).longValue();
        } else {
            rowsCount = items.size();
        }

        @SuppressWarnings("rawtypes")
        Pagination pagination = new Pagination(pageIndex, pageSize, rowsCount);
        pagination.setItems(items);
        return pagination;
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    public <T> Pagination<T> findPagination(CharSequence queryString, Map<String, Object> params, int pageIndex,
            int pageSize) {
        Query query = getSession().createQuery(queryString.toString());

        if ((pageSize > 0) && (pageIndex > 0)) {
            query.setFirstResult((pageIndex < 2) ? 0 : (pageIndex - 1) * pageSize);
            query.setMaxResults(pageSize);
        }

        setParameter(query, params);
        @SuppressWarnings({ "rawtypes" })
        List items = query.list();
        long rowsCount = 0L;

        if ((pageSize > 0) && (pageIndex > 0)) {
            String hql = parseSelectCount(queryString.toString());
            Object count = get(hql, params);
            if(count != null){
            	rowsCount = ((Long)count).longValue();
            }
        } else {
            rowsCount = items.size();
        }

        @SuppressWarnings("rawtypes")
        Pagination pagination = new Pagination(pageIndex, pageSize, rowsCount);
        pagination.setItems(items);
        return pagination;
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    public <T> Pagination<T> findPagination(CharSequence queryString, CharSequence countString, int pageIndex,
            int pageSize, Object... params) {
        Query query = getSession().createQuery(queryString.toString());

        if ((pageSize > 0) && (pageIndex > 0)) {
            query.setFirstResult((pageIndex < 2) ? 0 : (pageIndex - 1) * pageSize);
            query.setMaxResults(pageSize);
        }

        for (int i = 0; i < params.length; ++i) {
            query.setParameter(i, params[i]);
        }
        @SuppressWarnings("rawtypes")
        List items = query.list();
        long rowsCount = 0L;

        if ((pageSize > 0) && (pageIndex > 0)) {
            rowsCount = ((Long) get(countString, params)).longValue();
        } else
            rowsCount = items.size();

        @SuppressWarnings("rawtypes")
        Pagination pagination = new Pagination(pageIndex, pageSize, rowsCount);
        pagination.setItems(items);
        return pagination;
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    public <T> Pagination<T> findPagination(CharSequence queryString, CharSequence countString,
            Map<String, Object> params, int pageIndex, int pageSize) {
        Query query = getSession().createQuery(queryString.toString());

        if ((pageSize > 0) && (pageIndex > 0)) {
            query.setFirstResult((pageIndex < 2) ? 0 : (pageIndex - 1) * pageSize);
            query.setMaxResults(pageSize);
        }

        setParameter(query, params);
        @SuppressWarnings("rawtypes")
        List items = query.list();
        long rowsCount = 0L;

        if ((pageSize > 0) && (pageIndex > 0)) {
            rowsCount = ((Long) get(countString, params)).longValue();
        } else
            rowsCount = items.size();

        @SuppressWarnings("rawtypes")
        Pagination pagination = new Pagination(pageIndex, pageSize, rowsCount);
        pagination.setItems(items);
        return pagination;
    }

    @SuppressWarnings({ "serial", "unchecked", "hiding" })
    public <T> Pagination<T> findSqlPagination(CharSequence queryString, final CharSequence countString,
            final Map<String, Object> params, int pageIndex, int pageSize) {
        SQLQuery query = getSession().createSQLQuery(queryString.toString());
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        if ((pageSize > 0) && (pageIndex > 0)) {
            query.setFirstResult((pageIndex < 2) ? 0 : (pageIndex - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        if ((params != null) && (!(params.isEmpty()))) {
            setParameter(query, params);
        }
        @SuppressWarnings("rawtypes")
        List items = query.list();
        BigInteger rowsCount = new BigInteger("0");
        if ((pageSize > 0) && (pageIndex > 0)) {
        	rowsCount = (BigInteger) executeQuery(new HibernateHandler() {
                public Object doInHibernate(Session session) {
                    SQLQuery query = session.createSQLQuery(countString.toString());
                    if ((params != null) && (!(params.isEmpty()))) {
                        setParameter(query, params);
                    }
                    return query.uniqueResult();
                }
            });
        }

        @SuppressWarnings("rawtypes")
        Pagination pagination = new Pagination(pageIndex, pageSize, rowsCount.intValue());
        pagination.setItems(items);
        return pagination;
    }

    public Object executeQuery(HibernateHandler handler) {
        return handler.doInHibernate(getSession());
    }

    public void execute(String hql) {
        executeUpdate(hql);
    }

    public void execute(HibernateHandler handler) {
        executeUpdate(handler);
    }

    public void executeSql(String sql) {
        executeSqlUpdate(sql);
    }

    public int executeSqlUpdate(String sql) {
        return getSession().createSQLQuery(sql).executeUpdate();
    }

    public int executeUpdate(String hql) {
        return getSession().createQuery(hql).executeUpdate();
    }

    public Object executeUpdate(HibernateHandler handler) {
        return handler.doInHibernate(getSession());
    }

    protected Query setParameter(Query query, Map<String, Object> parameterMap) {
        for (@SuppressWarnings("rawtypes")
        Iterator iterator = parameterMap.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            if (parameterMap.get(key) != null) {
            	query.setParameter(key, parameterMap.get(key));
			}
        }
        return query;
    }

    protected boolean followWithWord(String s, String sub, int pos) {
        int i = 0;
        for (; (pos < s.length()) && (i < sub.length()); ++i) {
            if (s.charAt(pos) != sub.charAt(i))
                return false;
            ++pos;
        }

        if (i < sub.length()) {
            return false;
        }

        if (pos >= s.length()) {
            return true;
        }
        return (!(isAlpha(s.charAt(pos))));
    }

    protected String parseSelectCount(String queryString) {
        String hql = queryString.toLowerCase();
        int noBlankStart = 0;
        for (int len = hql.length(); noBlankStart < len; ++noBlankStart) {
            if (hql.charAt(noBlankStart) > ' ') {
                break;
            }
        }

        int pair = 0;

        if (!(followWithWord(hql, "select", noBlankStart))) {
            pair = 1;
        }
        int fromPos = -1;
        for (int i = noBlankStart; i < hql.length();) {
            if (followWithWord(hql, "select", i)) {
                ++pair;
                i += "select".length();
            } else if (followWithWord(hql, "from", i)) {
                --pair;
                if (pair == 0) {
                    fromPos = i;
                    break;
                }
                i += "from".length();
            } else {
                ++i;
            }
        }
        if (fromPos == -1) {
            throw new IllegalArgumentException("parse count sql error, check your sql/hql");
        }

        String countHql = "select count(*) " + queryString.substring(fromPos);
        return countHql;
    }

    protected boolean isAlpha(char c) {
        return ((c == '_') || (('0' <= c) && (c <= '9')) || (('a' <= c) && (c <= 'z')) || (('A' <= c) && (c <= 'Z')));
    }

    public void delete(Serializable id) {
        T entity = getById(id);
        delete(entity);
    }

    public void delete(List<T> entitys) {
        for (T entity : entitys) {
            delete(entity);
        }
    }

    public T getById(Serializable id) {
        if (id == null)
            return null;

        return (T) get(entityClazz, id);
    }

    @Override
    public T saveEntity(T o) {
        saveOrUpdate(o);
        return o;
    }

    @Override
    public void save(List<T> list) {
        saveOrUpdateAll(list);
    }

    @Override
    public T insert(T entity) {
        save(entity);
        return entity;
    }

    @Override
    public void insert(List<T> entitys) {
        for (T entity : entitys) {
            save(entity);
        }
    }

    @Override
    public void update(List<T> entitys) {
        for (T entity : entitys) {
            update(entity);
        }
    }

    @Override
    public List<T> findByProperty(String name, Object value) {
        String hql = "from  " + entityClazz.getSimpleName() + " where " + name + "=? ";
        return findList(hql, value);
    }

    @Override
    public List<T> findByProperty(Map<String, Object> conditionMap) {
        StringBuilder hql = new StringBuilder();
        hql.append("from  " + entityClazz.getSimpleName());
        if (!conditionMap.isEmpty()) {
            Iterator<String> it = conditionMap.keySet().iterator();
            String key = it.next();
            hql.append(" where  " + key + "=:" + key);
            while (it.hasNext()) {
                key = it.next();
                hql.append(" and  " + key + "=:" + key);
            }
        }
        return findList(hql.toString(), conditionMap);
    }

    @Override
    public <V> List<V> findListByMax(final CharSequence queryString, final int maxResults, final Object... params) {
        @SuppressWarnings({ "unchecked", "serial" })
        List<V> list = (List<V>) executeQuery(new HibernateHandler() {
            @Override
            public List<V> doInHibernate(Session paramSession) {
                try {
                    Query query = paramSession.createQuery(queryString.toString());
                    for (int i = 0; i < params.length; ++i) {
                        query.setParameter(i, params[i]);
                    }
                    return query.setMaxResults(maxResults).list();
                } catch (RuntimeException re) {
                    throw re;
                }
            }
        });
        return list;
    }

    @Override
    public <V> List<V> findListByMax(final CharSequence queryString, final int maxResults,
            final Map<String, Object> params) {
        @SuppressWarnings({ "unchecked", "serial" })
        List<V> list = (List<V>) executeQuery(new HibernateHandler() {
            @Override
            public List<V> doInHibernate(Session paramSession) {
                try {
                    Query query = paramSession.createQuery(queryString.toString());
                    for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext();) {
                        String key = iterator.next();
                        query.setParameter(key, params.get(key));
                    }
                    return query.setMaxResults(maxResults).list();
                } catch (RuntimeException re) {
                    throw re;
                }
            }

        });
        return list;
    }

    /**
     * HQL/SQL之数据操作命令(DML)拼接辅助类
     * 
     * @author PanJun
     * @deprecated by fu.zhanghua
     * 
     */
    public class DmlHelper {

        private ThreadLocal<Calendar> cal = new ThreadLocal<Calendar>() {
            @Override
            protected Calendar initialValue() {
                return Calendar.getInstance();
            }
        };

        /** HQL/SQL参数 */
        public final List<Object> paramList = new ArrayList<Object>();
        /** HQL/SQL语句 */
        public final StringBuilder dmlCmd = new StringBuilder();

        public DmlHelper() {
        }

        public DmlHelper(CharSequence dml, Object... params) {
            if (dml != null) {
                dmlCmd.append(dml);
                for (Object o : params) {
                    paramList.add(o);
                }
            }
        }

        @Override
        public String toString() {
            return "dml=" + dmlCmd + ", params=" + paramList;
        }

        public DmlHelper append(CharSequence dmlPart, Object... params) {
            if (dmlPart != null) {
                dmlCmd.append(" ").append(dmlPart);
                for (Object o : params) {
                    paramList.add(o);
                }
            }
            return this;
        }

        public DmlHelper addEqual(String fieldName, Object value, Object... nullVal) {
            if (value == null || fieldName == null) {
                return this;
            }

            if (value instanceof String) {
                value = value.toString().trim();
                if ("".equals(value)) {
                    return this;
                }
            }

            for (Object NULL : nullVal) {
                if (NULL == value) {
                    return this;
                }

                if (value.equals(NULL)) {
                    return this;
                }
            }

            dmlCmd.append(" and ").append(fieldName).append("=? ");
            paramList.add(value);
            return this;
        }

        public DmlHelper addLikeAll(String name, String value) {
            if (null == value || null == name)
                return this;

            value = "%" + value.trim().toLowerCase() + "%";
            dmlCmd.append(" and lower(").append(name).append(") like ? ");
            paramList.add(value);
            return this;
        }

        /**
         * 清除时间里的时分秒，只留日期
         * 
         * @param calendar
         */
        private void clearTime(Calendar calendar) {
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH);
            int d = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.clear();
            calendar.set(Calendar.YEAR, y);
            calendar.set(Calendar.MONTH, m);
            calendar.set(Calendar.DAY_OF_MONTH, d);
        }

        /**
         * 添加开始日期、结束日期(注意时分秒不记入查询条件)查询条件，包含开始日期和结束日期
         * 
         * @param fieldName
         *            hbm对象属性名称或字段名
         * @param minDay
         *            开始日期
         * @param maxDay
         *            结果日期
         */
        public DmlHelper addDayRange(String fieldName, Date minDay, Date maxDay) {
            Calendar calendar = cal.get();
            if (minDay != null) {
                calendar.setTime(minDay);
                clearTime(calendar);
                calendar.add(Calendar.SECOND, -1);
                dmlCmd.append(" and ").append(fieldName).append(">? ");
                paramList.add(calendar.getTime());
            }

            if (maxDay != null) {
                calendar.setTime(maxDay);
                clearTime(calendar);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                dmlCmd.append(" and ").append(fieldName).append("<? ");
                paramList.add(calendar.getTime());
            }
            return this;
        }

        /**
         * 添加开始时间、结束时间查询条件，包含开始时间和结束时间
         * 
         * @param fieldName
         *            hbm对象属性名称或字段名
         * @param minTime
         *            开始时间
         * @param maxTime
         *            结果时间
         */
        public DmlHelper addDayRange(String fieldName, String minTime, String maxTime) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start_ = null, end_ = null;
            if (ObjectUtil.hasText(minTime) && ObjectUtil.hasText(maxTime)) {
                try {
                    start_ = sdf.parse(minTime);
                    end_ = sdf.parse(maxTime);
                } catch (ParseException e) {
                }
            }

            return addTimeRange(fieldName, start_, end_);
        }

        /**
         * 添加开始时间、结束时间查询条件，包含开始时间和结束时间
         * 
         * @param fieldName
         *            hbm对象属性名称或字段名
         * @param minTime
         *            开始时间
         * @param maxTime
         *            结果时间
         */
        public DmlHelper addTimeRange(String fieldName, Date minTime, Date maxTime) {
            if (minTime != null) {
                dmlCmd.append(" and ").append(fieldName).append(">? ");
                paramList.add(minTime);
            }

            if (maxTime != null) {
                dmlCmd.append(" and ").append(fieldName).append("<? ");
                paramList.add(maxTime);
            }
            return this;
        }

        public <D> Pagination<D> findPagin(int pageIndex, int pageSize) {
            String hql = dmlCmd.toString();
            Object[] allParams = new Object[paramList.size()];
            int i = 0;
            for (Object o : paramList) {
                allParams[i++] = o;
            }
            return findPagination(hql, pageIndex, pageSize, allParams);
        }

    }

    /**
     * 获取所有数据表
     *
     * @return
     */
    public Integer getAllDbTableSize() {
        SessionFactory factory = getSession().getSessionFactory();
        Map<String, ClassMetadata> metaMap = factory.getAllClassMetadata();
        return metaMap.size();
    }

    /**
     * 根据实体名字获取唯一记录
     *
     * @param propertyName
     * @param value
     * @return
     */
    public <T> T findUniqueByProperty(Class<T> entityClass,
                                      String propertyName, Object value) {
        Assert.hasText(propertyName);
        return (T) createCriteria(entityClass,
                Restrictions.eq(propertyName, value)).uniqueResult();
    }

    /**
     * 按属性查找对象列表.
     */
    public <T> List<T> findByProperty(Class<T> entityClass,
                                      String propertyName, Object value) {
        Assert.hasText(propertyName);
        return (List<T>) createCriteria(entityClass,
                Restrictions.eq(propertyName, value)).list();
    }


    /**
     * 批量保存数据
     *
     * @param <T>
     * @param entitys
     *            要持久化的临时实体对象集合
     */
    public <T> void batchSave(List<T> entitys) {
        for (int i = 0; i < entitys.size(); i++) {
            getSession().save(entitys.get(i));
            if (i % 1000 == 0) {
                // 1000个对象批量写入数据库，后才清理缓存
                getSession().flush();
                getSession().clear();
            }
        }
        //最后页面的数据，进行提交手工清理
        getSession().flush();
        getSession().clear();
    }



    /**
     * 根据主键删除指定的实体
     *
     * @param <T>
     * @param entityName
     * @param id
     */
    public <T> void deleteEntityById(Class entityName, Serializable id) {
        delete(get(entityName, id));
        //getSession().flush();
    }

    /**
     * 删除全部的实体
     *
     * @param <T>
     *
     * @param entitys
     */
    public <T> void deleteAllEntitie(Collection<T> entitys) {
        for (Object entity : entitys) {
            getSession().delete(entity);
            //getSession().flush();
        }
    }

    /**
     * 根据主键获取实体并加锁。
     *
     * @param <T>
     * @param entityName
     * @param id
     * @return
     */
    public <T> T getEntity(Class entityName, Serializable id) {

        T t = (T) getSession().get(entityName, id);
        if (t != null) {
            //getSession().flush();
        }
        return t;
    }

    /**
     * 更新指定的实体
     *
     * @param <T>
     * @param pojo
     */
    public <T> void updateEntitie(T pojo) {
        getSession().update(pojo);
        //getSession().flush();
    }

    /**
     * 更新指定的实体
     *
     * @param <T>
     * @param className
     * @param id
     */
    public <T> void updateEntitie(String className, Object id) {
        getSession().update(className, id);
        //getSession().flush();
    }

    /**
     * 通过hql 查询语句查找对象
     *
     * @param query
     * @return
     */
    public List<T> findByQueryString(final String query) {

        Query queryObject = getSession().createQuery(query);
        List<T> list = queryObject.list();
//		if (list.size() > 0) {
        //getSession().flush();
//		}
        return list;

    }

    /**
     * 通过hql 查询语句查找HashMap对象
     *
     * @param hql
     * @return
     */
    public Map<Object, Object> getHashMapbyQuery(String hql) {

        Query query = getSession().createQuery(hql);
        List list = query.list();
        Map<Object, Object> map = new HashMap<Object, Object>();
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Object[] tm = (Object[]) iterator.next();
            map.put(tm[0].toString(), tm[1].toString());
        }
        return map;

    }

    /**
     * 通过sql更新记录
     * @param query
     * @return
     */
    public int updateBySqlString(final String query) {

        Query querys = getSession().createSQLQuery(query);
        return querys.executeUpdate();
    }

    /**
     * 通过sql查询语句查找对象
     *
     * @param sql
     * @return
     */
    public List<T> findListbySql(final String sql) {
        Query querys = getSession().createSQLQuery(sql);
        return querys.list();
    }

    /**
     * 创建Criteria对象，有排序功能。
     *
     * @param <T>
     * @param entityClass
     * @param isAsc
     * @param criterions
     * @return
     */
    private <T> Criteria createCriteria(Class<T> entityClass, boolean isAsc,
                                        Criterion... criterions) {
        Criteria criteria = createCriteria(entityClass, criterions);
        if (isAsc) {
            criteria.addOrder(Order.asc("asc"));
        } else {
            criteria.addOrder(Order.desc("desc"));
        }
        return criteria;
    }

    /**
     * 创建Criteria对象带属性比较
     *
     * @param <T>
     * @param entityClass
     * @param criterions
     * @return
     */
    private <T> Criteria createCriteria(Class<T> entityClass,
                                        Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(entityClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    public <T> List<T> loadAll(final Class<T> entityClass) {
        Criteria criteria = createCriteria(entityClass);
        return criteria.list();
    }

    /**
     * 创建单一Criteria对象
     *
     * @param <T>
     * @param entityClass
     * @return
     */
    private <T> Criteria createCriteria(Class<T> entityClass) {
        Criteria criteria = getSession().createCriteria(entityClass);
        return criteria;
    }

    /**
     * 根据属性名和属性值查询. 有排序
     *
     * @param <T>
     * @param entityClass
     * @param propertyName
     * @param value
     * @param isAsc
     * @return
     */
    public <T> List<T> findByPropertyisOrder(Class<T> entityClass,
                                             String propertyName, Object value, boolean isAsc) {
        Assert.hasText(propertyName);
        return createCriteria(entityClass, isAsc,
                Restrictions.eq(propertyName, value)).list();
    }

    /**
     * 根据属性名和属性值 查询 且要求对象唯一.
     *
     * @return 符合条件的唯一对象.
     */
    public <T> T findUniqueBy(Class<T> entityClass, String propertyName,
                              Object value) {
        Assert.hasText(propertyName);
        return (T) createCriteria(entityClass,
                Restrictions.eq(propertyName, value)).uniqueResult();
    }

    /**
     * 根据查询条件与参数列表创建Query对象
     *
     * @param session
     *            Hibernate会话
     * @param hql
     *            HQL语句
     * @param objects
     *            参数列表
     * @return Query对象
     */
    public Query createQuery(Session session, String hql, Object... objects) {
        Query query = session.createQuery(hql);
        if (objects != null) {
            for (int i = 0; i < objects.length; i++) {
                query.setParameter(i, objects[i]);
            }
        }
        return query;
    }

    /**
     * 根据实体名返回全部对象
     *
     * @param <T>
     * @param hql
     * @param size
     * @return
     */
    /**
     * 使用占位符的方式填充值 请注意：like对应的值格式："%"+username+"%" Hibernate Query
     *
     * @param hql
     *            占位符号?对应的值，顺序必须一一对应 可以为空对象数组，但是不能为null
     * @return 2008-07-19 add by liuyang
     */
    public List<T> executeQuery(final String hql, final Object[] values) {
        Query query = getSession().createQuery(hql);
        // query.setCacheable(true);
        for (int i = 0; values != null && i < values.length; i++) {
            query.setParameter(i, values[i]);
        }

        return query.list();

    }


    /**
     * 调用存储过程
     */
    public void callableStatementByName(String proc) {
    }

    /**
     * 通过hql 查询语句查找对象
     *
     * @param <T>
     * @param hql
     * @param param
     * @return
     */
    public <T> List<T> findHql(String hql, Object... param) {
        Query q = getSession().createQuery(hql);
        if (param != null && param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                q.setParameter(i, param[i]);
            }
        }
        return q.list();
    }

    /**
     * 执行HQL语句操作更新
     *
     * @param hql
     * @return
     */
    public Integer executeHql(String hql) {
        Query q = getSession().createQuery(hql);
        return q.executeUpdate();
    }

    /**
     * 调用存储过程
     */
    @SuppressWarnings({ "unchecked",})
    public <T> List<T> executeProcedure(String executeSql,Object... params) {
        SQLQuery sqlQuery = getSession().createSQLQuery(executeSql);

        for(int i=0;i<params.length;i++){
            sqlQuery.setParameter(i, params[i]);
        }

        return sqlQuery.list();
    }

}