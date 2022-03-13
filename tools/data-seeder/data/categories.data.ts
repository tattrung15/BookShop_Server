import { categories as Category } from "@prisma/client";
import { slugify } from "../util";

const categoryNames: string[] = [
  "Lịch sử truyền thống", // 0
  "Sách công cụ Đoàn - Đội", // 1
  "Kiến thức - Khoa học", // 2
  "Văn học Việt Nam", // 3
  "Văn học nước ngoài", // 4
  "Truyện tranh", // 5
  "Manga - comic", // 6
  "Wings Books", // 7
  "Giải mã bản thân", // 8
  "Combo", // 9
];

export const categories: Category[] = categoryNames.map(
  (item: string, index: number) => ({
    id: BigInt(index + 1),
    name: item,
    description: null,
    parent_category_id: null,
    slug: slugify(item),
    is_author: false,
    created_at: new Date(),
    updated_at: new Date(),
  })
);
