import { PrismaClient } from "@prisma/client";
import { banners } from "./data/banners.data";
import { categories } from "./data/categories.data";
import { products } from "./data/products.data";

const prisma = new PrismaClient();

async function seedData() {
  await prisma.banners.createMany({ data: banners });
  await prisma.$executeRawUnsafe(
    `ALTER SEQUENCE banners_id_seq RESTART WITH ${banners.length + 1}`
  );
  await prisma.categories.createMany({ data: categories });
  await prisma.$executeRawUnsafe(
    `ALTER SEQUENCE categories_id_seq RESTART WITH ${categories.length + 1}`
  );
  await prisma.products.createMany({ data: products });
  await prisma.$executeRawUnsafe(
    `ALTER SEQUENCE products_id_seq RESTART WITH ${products.length + 1}`
  );
}

try {
  seedData();
  console.log("Seed data successfully");
} catch (e) {
  console.log("Seed data failed");
  console.log(e);
}
