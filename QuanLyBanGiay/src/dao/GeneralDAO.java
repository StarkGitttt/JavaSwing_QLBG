/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

/**
 *
 * @author DELL
 */
public abstract class GeneralDAO<E, K> {
    public abstract int insert(E entity);
    public abstract int update (E entity);
    public abstract int delete (K key);
    public abstract List<E> selectAll ();
    public abstract E selectByID(K key);
    protected abstract List<E> selectBySQL (String sql, Object...obj);
}
