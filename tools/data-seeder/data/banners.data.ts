import { banners as Banner } from "@prisma/client";
import { BannerType } from "../util";

export const banners: Banner[] = [
  {
    id: BigInt(1),
    title: "Nhân vật hạ cấp Tomozaki",
    image_url: null,
    is_active: false,
    type: BannerType.PRODUCT,
    created_at: new Date(),
    updated_at: new Date(),
  },
  {
    id: BigInt(2),
    title: "'Cậu' ma nhà xí Hanako",
    image_url: null,
    is_active: false,
    type: BannerType.CATEGORY,
    created_at: new Date(),
    updated_at: new Date(),
  },
  {
    id: BigInt(3),
    title: "Nhà có cụ mèo già",
    image_url: null,
    is_active: false,
    type: BannerType.PRODUCT,
    created_at: new Date(),
    updated_at: new Date(),
  },
  {
    id: BigInt(4),
    title: "Nhật kí những trái tim xanh đỏ",
    image_url: null,
    is_active: false,
    type: BannerType.PRODUCT,
    created_at: new Date(),
    updated_at: new Date(),
  },
];
