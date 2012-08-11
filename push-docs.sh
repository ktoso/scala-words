#!/bin/sh
cd docs

make html

cd ..

git stash
git checkout gh-pages

cp -r docs/_build/html/* .
rm -rf target/ docs/

find ./**/*html -type f -exec gsed -i 's/_static/static/' {} \;
find ./*html -type f -exec gsed -i 's/_static/static/' {} \;

find ./*html -type f -exec gsed -i 's/_sources/sources/' {} \;
find ./**/*html -type f -exec gsed -i 's/_sources/sources/' {} \;

rm -rf static
mkdir static
mv _sources static/
mv _static static/

git add .
git ci -m "Updating documentation @ $(date)"
git push origin gh-pages

git stash && git stash drop
git checkout master

echo "Done!"
