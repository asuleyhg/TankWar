package com.yihg.tank.chainofresponsebility;

import com.yihg.tank.AbstractGameObject;
import com.yihg.tank.PropertyMgr;

import java.util.ArrayList;
import java.util.List;

public class ColliderChain implements Collider{
    private List<Collider> colliders;

    public ColliderChain(){
        initCollider();
    }

    private void initCollider() {
        colliders = new ArrayList<>();
        String[] names = PropertyMgr.get("colliders").split(",");
        try {
            for (String name : names){
                Class clazz = Class.forName("com.yihg.tank.chainofresponsebility." + name);
                Collider c = (Collider)clazz.getConstructor().newInstance();
                colliders.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean collide(AbstractGameObject go1, AbstractGameObject go2){
        for(Collider collider : colliders){
            if (!collider.collide(go1, go2)){
                return false;
            }
        }
        return true;
    }
}
