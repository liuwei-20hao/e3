package com.e3.content.service.impl;

import com.e3.content.service.ContentCategoryService;
import com.e3.mapper.TbContentCategoryMapper;
import com.e3.pojo.E3Result;
import com.e3.pojo.EasyUITreeNode;
import com.e3.pojo.TbContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCategoryList(long parentId) {
        List<TbContentCategory> contentCategoryList = tbContentCategoryMapper.getContentCategoryList(parentId);
        List<EasyUITreeNode>  nodelist = new ArrayList<>();
        for (TbContentCategory contentCategory:contentCategoryList){
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(contentCategory.getId());
            node.setState(contentCategory.getIsParent()?"closed":"open");;
            node.setText(contentCategory.getName());
            nodelist.add(node);
        }
        return nodelist;
    }

    @Override
    public E3Result addContentCategory(long parentId,String name) {
        //创建一个tb_content_category表对应的pojo对象
        TbContentCategory contentCategory = new TbContentCategory();
        //设置pojo的属性
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        //1(正常),2(删除)
        contentCategory.setStatus(1);
        //默认排序就是1
        contentCategory.setSortOrder(1);
        //新添加的节点一定是叶子节点
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //插入到数据库
        tbContentCategoryMapper.insert(contentCategory);
        //判断父节点的isparent属性。如果不是true改为true
        //根据parentid查询父节点
        TbContentCategory parent = tbContentCategoryMapper.selectById(parentId);
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            //更新到数数据库
            tbContentCategoryMapper.updateById(parent);
        }
        //返回结果，返回E3Result，包含pojo
        return E3Result.ok(contentCategory);
    }

    @Override
    public E3Result updateContentCategory(long parentId, String name){
        //创建一个tb_content_category表对应的pojo对象
        TbContentCategory contentCategory = new TbContentCategory();
        //设置pojo的属性
        contentCategory.setId(parentId);
        contentCategory.setName(name);
        contentCategory.setUpdated(new Date());
        tbContentCategoryMapper.updateNameById(contentCategory);
        return E3Result.ok();
    }

    @Override
    public E3Result deleteContentCategory(long id) {
        //获取当前节点
        TbContentCategory node = tbContentCategoryMapper.selectById(id);
        //删除此节点
        this.delete(id);
        //判断是否更新父节点
        this.updateParentNode(node.getParentId());
        return E3Result.ok();
    }

    public void delete(Long id){
        //获取当前节点下的子节点
        List<TbContentCategory> list = tbContentCategoryMapper.getListByParentId(id);
        //若无子节点
        if(list.size()==0){
            //获取当前节点
            TbContentCategory deleteNode = tbContentCategoryMapper.selectById(id);
            //得到此节点的父节点Id
            Long parentId = deleteNode.getParentId();
            //删除此节点
            tbContentCategoryMapper.deleteById(id);
            //删除此节点后，判断此节点的父节点是否有子节点
            this.updateParentNode(parentId);
        }else{
            tbContentCategoryMapper.deleteById(id);
            for(TbContentCategory l:list){
                if(l.getIsParent()){
                    //递归删除节点
                    this.delete(l.getId());
                }else{
                    tbContentCategoryMapper.deleteById(l.getId());
                }
            }
        }
    }

    /**
     * 判断此节点是否存在兄弟节点，若不存在，则将其父节点更新成子节点
     * @param
     */
    private void updateParentNode(Long parentId) {
        //查询父节点下还有没有子节点
        List<TbContentCategory> contentCat = tbContentCategoryMapper.getListByParentId(parentId);
        //若无子节点
        if (contentCat.size() == 0) {
            //更新此节点的父节点为子节点
            TbContentCategory node2 = tbContentCategoryMapper.selectById(parentId);
            node2.setIsParent(false);
            tbContentCategoryMapper.updateIsParentById(node2);
        }
    }
}
