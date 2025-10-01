// package com.sparta.team5.fractal.domain.product.entity;
//
// import com.sparta.team5.fractal.domain.category.entity.Category;
// import com.sparta.team5.fractal.domain.tag.entity.Tag;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
//
// import java.math.BigDecimal;
// import java.util.Set;
//
// import static org.assertj.core.api.Assertions.*;
//
// class ProductTest {
//
//     @Test
//     @DisplayName("상품 생성 성공")
//     void createProduct_Success() {
//         // given
//         String title = "갤럭시 워치 4";
//         BigDecimal price = BigDecimal.valueOf(100000);
//         String description = "최신형 전자기기";
//
//         // when
//         Product product = Product.of(title, price, description);
//
//         // then
//         assertThat(product.getTitle()).isEqualTo(title);
//         assertThat(product.getPrice()).isEqualTo(price);
//         assertThat(product.getDescription()).isEqualTo(description);
//         assertThat(product.getProductCategories()).isEmpty();
//         assertThat(product.getProductTags()).isEmpty();
//         assertThat(product.isDeleted()).isFalse();
//     }
//
//     @Test
//     @DisplayName("상품 정보 수정 성공")
//     void updateProduct_Success() {
//         // given
//         Product product = Product.of("갤럭시 워치 4", BigDecimal.valueOf(100000), "기존 설명");
//         String newTitle = "갤럭시 워치 5";
//         BigDecimal newPrice = BigDecimal.valueOf(120000);
//         String newDescription = "업데이트된 설명";
//
//         // when
//         product.update(newTitle, newPrice, newDescription);
//
//         // then
//         assertThat(product.getTitle()).isEqualTo(newTitle);
//         assertThat(product.getPrice()).isEqualTo(newPrice);
//         assertThat(product.getDescription()).isEqualTo(newDescription);
//     }
//
//     @Test
//     @DisplayName("카테고리 추가 성공")
//     void addCategory_Success() {
//         // given
//         Product product = Product.of("갤럭시 워치 4", BigDecimal.valueOf(100000), "최신형 전자기기");
//         Category category1 = Category.of("전자제품", null);
//         Category category2 = Category.of("웨어러블", category1);
//
//         // when
//         product.addCategory(category1);
//         product.addCategory(category2);
//
//         // then
//         assertThat(product.getProductCategories()).hasSize(2);
//     }
//
//     @Test
//     @DisplayName("중복 카테고리 추가 시 한 번만 추가됨")
//     void addCategory_DuplicateCategory_OnlyOneAdded() {
//         // given
//         Product product = Product.of("갤럭시 워치 4", BigDecimal.valueOf(100000), "최신형 전자기기");
//         Category category = Category.of("전자제품", null);
//
//         // when
//         product.addCategory(category);
//         product.addCategory(category); // 중복 추가
//
//         // then
//         assertThat(product.getProductCategories()).hasSize(1);
//     }
//
//     @Test
//     @DisplayName("카테고리 교체 성공")
//     void replaceCategories_Success() {
//         // given
//         Product product = Product.of("갤럭시 워치 4", BigDecimal.valueOf(100000), "최신형 전자기기");
//         Category category1 = Category.of("전자제품", null);
//         Category category2 = Category.of("웨어러블", category1);
//         Category category3 = Category.of("스마트워치", category2);
//
//         product.addCategory(category1);
//         product.addCategory(category2);
//
//         // when
//         product.replaceCategories(Set.of(category2, category3));
//
//         // then
//         assertThat(product.getProductCategories()).hasSize(2);
//     }
//
//     @Test
//     @DisplayName("카테고리 제거 성공")
//     void removeCategory_Success() {
//         // given
//         Product product = Product.of("갤럭시 워치 4", BigDecimal.valueOf(100000), "최신형 전자기기");
//         Category category1 = Category.of("전자제품", null);
//         Category category2 = Category.of("웨어러블", category1);
//
//         product.addCategory(category1);
//         product.addCategory(category2);
//
//         // when
//         product.removeCategory(category1);
//
//         // then
//         assertThat(product.getProductCategories()).hasSize(1);
//     }
//
//     @Test
//     @DisplayName("태그 추가 성공")
//     void addTag_Success() {
//         // given
//         Product product = Product.of("갤럭시 워치 4", BigDecimal.valueOf(100000), "최신형 전자기기");
//         Tag tag1 = Tag.from("시계");
//         Tag tag2 = Tag.from("트랜디");
//
//         // when
//         product.addTag(tag1);
//         product.addTag(tag2);
//
//         // then
//         assertThat(product.getProductTags()).hasSize(2);
//     }
//
//     @Test
//     @DisplayName("중복 태그 추가 시 한 번만 추가됨")
//     void addTag_DuplicateTag_OnlyOneAdded() {
//         // given
//         Product product = Product.of("갤럭시 워치 4", BigDecimal.valueOf(100000), "최신형 전자기기");
//         Tag tag = Tag.from("시계");
//
//         // when
//         product.addTag(tag);
//         product.addTag(tag); // 중복 추가
//
//         // then
//         assertThat(product.getProductTags()).hasSize(1);
//     }
//
//     @Test
//     @DisplayName("태그 교체 성공")
//     void replaceTags_Success() {
//         // given
//         Product product = Product.of("갤럭시 워치 4", BigDecimal.valueOf(100000), "최신형 전자기기");
//         Tag tag1 = Tag.from("시계");
//         Tag tag2 = Tag.from("트랜디");
//         Tag tag3 = Tag.from("스마트워치");
//
//         product.addTag(tag1);
//         product.addTag(tag2);
//
//         // when
//         product.replaceTags(Set.of(tag2, tag3));
//
//         // then
//         assertThat(product.getProductTags()).hasSize(2);
//     }
//
//     @Test
//     @DisplayName("태그 제거 성공")
//     void removeTag_Success() {
//         // given
//         Product product = Product.of("갤럭시 워치 4", BigDecimal.valueOf(100000), "최신형 전자기기");
//         Tag tag1 = Tag.from("시계");
//         Tag tag2 = Tag.from("트랜디");
//
//         product.addTag(tag1);
//         product.addTag(tag2);
//
//         // when
//         product.removeTag(tag1);
//
//         // then
//         assertThat(product.getProductTags()).hasSize(1);
//     }
//
//     @Test
//     @DisplayName("상품 소프트 삭제 성공")
//     void deleteProduct_Success() {
//         // given
//         Product product = Product.of("갤럭시 워치 4", BigDecimal.valueOf(100000), "최신형 전자기기");
//
//         // when
//         product.delete();
//
//         // then
//         assertThat(product.isDeleted()).isTrue();
//         assertThat(product.getDeletedAt()).isNotNull();
//     }
//
//     @Test
//     @DisplayName("null 카테고리 추가 시 예외 발생")
//     void addCategory_NullCategory_ThrowsException() {
//         // given
//         Product product = Product.of("갤럭시 워치 4", BigDecimal.valueOf(100000), "최신형 전자기기");
//
//         // when & then
//         assertThatThrownBy(() -> product.addCategory(null))
//                 .isInstanceOf(IllegalArgumentException.class)
//                 .hasMessage("카테고리는 null일 수 없습니다");
//     }
//
//     @Test
//     @DisplayName("null 태그 추가 시 예외 발생")
//     void addTag_NullTag_ThrowsException() {
//         // given
//         Product product = Product.of("갤럭시 워치 4", BigDecimal.valueOf(100000), "최신형 전자기기");
//
//         // when & then
//         assertThatThrownBy(() -> product.addTag(null))
//                 .isInstanceOf(IllegalArgumentException.class)
//                 .hasMessage("태그는 null일 수 없습니다");
//     }
// }
