import { PrismaClient } from "@prisma/client";
import { banners } from "./data/banners.data";
import { categories } from "./data/categories.data";
import { products } from "./data/products.data";

const prisma = new PrismaClient();

async function seedData() {
  await prisma.banners.createMany({ data: banners });
  await prisma.categories.createMany({ data: categories });
  await prisma.products.createMany({ data: products });
}

try {
  seedData();
  console.log("Seed data successful");
} catch (e) {
  console.log("Seed data failed");
  console.log(e);
}
