package com.glacier.glacierdiary.entity.DTO;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 编辑器开启 DTO
 * @since 2025/4/13 6:45
 */
public class EditorOpenDTO {

    private String id;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EditorOpenDTO{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
