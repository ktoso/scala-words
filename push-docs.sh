#!/bin/sh
cd docs

make html
make pdf

cd ..

git stash && git stash drop
git checkout gh-pages

cp -r docs/_build/html/* .
rm -rf target/ docs/

mv _static static
find ./ -type f -exec sed -i ‘s/_static/static/’ {} \;
mv _sources sources
find ./ -type f -exec sed -i ‘s/_sources/sources/’ {} \;

git add .
git ci -m "Updating documentation @ $(date)"
git push origin gh-pages

git stash && git stash drop
git checkout master

echo "Done!"
